package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.requests.OrderRequestDto;
import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.dtos.responses.OrderItemResponseDto;
import com.seng22212project.bitebliss.dtos.responses.OrderResponseDto;
import com.seng22212project.bitebliss.enums.ApiResponseStatus;
import com.seng22212project.bitebliss.enums.OrderPaymentStatus;
import com.seng22212project.bitebliss.enums.OrderStatus;
import com.seng22212project.bitebliss.exceptions.ProductNotFoundException;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.repositories.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class OrderServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired NotificationService notificationService;

    // Order Create method
    public ResponseEntity<ApiResponseDto<?>> createOrder(OrderRequestDto request) throws UserNotFoundException {

        try {
            User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found!"));
            Cart cart = this.cartRepo.findById(request.getCartId()).orElseThrow(() -> new RuntimeException("Cart not found!"));
            System.out.println(request.getCartId());

            if (cart.getUser().getId() != request.getUserId()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), "Invalid request to create an order!")
                );
            }
            Order order = new Order();
            order.setUser(request.getUserId());
            order.setFirstName(request.getFirstName());
            order.setLastName(request.getLastName());
            order.setAddressLine1(request.getAddressLine1());
            order.setAddressLine2(request.getAddressLine2());
            order.setCity(request.getCity());
            order.setDistrict(request.getDistrict());
            order.setPhoneNo(request.getPhone());
            order.setOrderAmt(request.getTotal());
            order.setPlacedOn(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.PENDING.name());
            order.setPaymentStatus(OrderPaymentStatus.UNPAID.name());
            order = orderRepo.save(order);

            Set<OrderItem> orderItems = saveOrderItems(cart, order);

            clearCart(cart);

            notificationService.sendOrderConfirmationEmail(user, order, orderItems);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "Order has been successfully placed!")
            );

        }catch(Exception e) {
            log.error("Failed to create order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), "Failed to create order: Please try again later!")
            );
        }
    }

//    @Override
    public ResponseEntity<ApiResponseDto<?>> getOrders(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));;
        List<Order> orders = orderRepo.findByUserOrderByOrderIdDesc(user.getId());
        List<OrderResponseDto> ordersResponse = new ArrayList<>();

        for(Order order: orders) {
            ordersResponse.add(orderToOrderResponseDto(order));
        }
        return ResponseEntity.ok(new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS.name(),
                ordersResponse
        ));

    }

    // Method to process cart items and create OrderItem objects
    private Set<OrderItem> saveOrderItems(Cart cart, Order order) {
        List<CartItem> cartItems =  cartItemRepository.findByCart(cart);
        Set<OrderItem> orderItems = new HashSet<>();

        for(CartItem cartItem: cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct().getProduct_id());
            orderItem.setProductQuantity(cartItem.getQuantity());
            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
            orderItems.add(orderItem);
            orderItem = orderItemRepository.save(orderItem);
        }

        return orderItems;
    }

    private void clearCart(Cart cart) {
        List<CartItem> cartItems =  cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(cartItems);
        cartRepo.delete(cart);
    }

    // Method to cancel an order
    public ResponseEntity<ApiResponseDto<?>> cancelOrder(int orderId) {
        try {
            Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
            order.setOrderStatus(OrderStatus.CANCELLED.name());
            orderRepo.save(order);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "Order has been successfully cancelled!")
            );
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), "Failed to cancel order: Please try again later!")
            );
        }
    }

    private OrderResponseDto orderToOrderResponseDto(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();
        for(OrderItem orderItem: orderItems) {
            orderItemResponseDtos.add(orderItemToOrderItemResponseDto(orderItem));
        }
        return new OrderResponseDto(
                order.getOrderId(),
                order.getPlacedOn(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getAddressLine1(),
                order.getAddressLine2(),
                order.getCity(),
                order.getDistrict(),
                order.getPhoneNo(),
                order.getOrderAmt(),
                orderItemResponseDtos
        );
    }

    private OrderItemResponseDto orderItemToOrderItemResponseDto(OrderItem orderItem) {
        Products product = productRepository.findById(orderItem.getProduct()).orElseThrow(ProductNotFoundException::new);
        return new OrderItemResponseDto(
                orderItem.getOrderItemId(),
                product.getProduct_id(),
                product.getProductName(),
                product.getPrice(),
                product.getImageUrl(),
                orderItem.getTotalProductPrice(),
                orderItem.getProductQuantity()
        );
    }

}

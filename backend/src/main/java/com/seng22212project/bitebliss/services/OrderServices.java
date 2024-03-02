
package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.*;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.payload.OrderResponse;
import com.seng22212project.bitebliss.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServices {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private JavaMailSender javaMailSender;

    // Order Create method
    public OrderDto orderCreate(OrderRequest request, Long userId) {
        // Retrieving user information by user ID
        User user = getUserById(userId);

        // Retrieving cart information
        int cartId = request.getCartId();
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(() -> new MissingResourceException("Cart not found"));

        // Mapping OrderRequest to OrderDto
        String addressLine1 = request.getAddressLine1();
        String addressLine2 = request.getAddressLine2();
        String city = request.getCity();
        String district = request.getDistrict();

        // Processing items in the cart and creating OrderItem objects
        Set<OrderItem> orderItems = processCartItems(cart);

        // Creating the Order object
        Order order = createOrderObject(user, addressLine1, addressLine2, city, district, orderItems);

        // Saving the order and clearing the cart
        Order savedOrder = saveOrderAndClearCart(order, cart);

        sendOrderConfirmationEmail(savedOrder, user.getEmail());

        return this.modelMapper.map(savedOrder, OrderDto.class);
    }

    // Method to get the user by user ID
    private User getUserById(Long userId) {
        return this.userRepo.findById(userId).orElseThrow(() -> new MissingResourceException("User not found"));
    }


    // Method to process cart items and create OrderItem objects
    private Set<OrderItem> processCartItems(Cart cart) {
        AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);
        return cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setProductQuantity(cartItem.getQuantity());
            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);

            totalOrderPrice.set(totalOrderPrice.get() + orderItem.getTotalProductPrice());

            return orderItem;
        }).collect(Collectors.toSet());
    }

    // Method to create the Order object
    private Order createOrderObject(User user, String addressLine1, String addressLine2, String city, String district, Set<OrderItem> orderItems) {
        Order order = new Order();
        order.setAddressLine1(addressLine1);
        order.setAddressLine2(addressLine2);
        order.setCity(city);
        order.setDistrict(district);
        order.setOrderStatus("created");
        order.setPaymentStatus("not paid");
        order.setUserId(user);
        order.setOrderItem(orderItems);
        order.setOrderAmt(orderItems.stream().mapToDouble(OrderItem::getTotalProductPrice).sum());

        return order;
    }

    // Method to save the order and clear the cart
    private Order saveOrderAndClearCart(Order order, Cart cart) {
        if (order.getOrderAmt() > 0) {
            Order savedOrder = this.orderRepo.save(order);
            cart.getItems().clear();
            this.cartRepo.save(cart);
            System.out.println("Hello");
            return savedOrder;
        } else {
            System.out.println(order.getOrderAmt());
            throw new MissingResourceException("Please add items to the cart before placing an order");
        }
    }

    // Method to cancel an order
    public void cancelOrder(int orderId) {
        Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new MissingResourceException("Order not found"));
        this.orderRepo.delete(order);
    }

    // Method to find an order by ID
    public OrderDto findById(int orderId) {
        Order order = this.orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return this.modelMapper.map(order, OrderDto.class);

    }

    // Method to retrieve all orders with pagination
//    public OrderResponse findAllOrders(int pageNumber, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<Order> findAll = this.orderRepo.findAll(pageable);
//        List<Order> content = findAll.getContent();
//
//        // Mapping Order objects to OrderDto
//        List<OrderDto> collect = content.stream().map(each -> this.modelMapper.map(each, OrderDto.class)).collect(Collectors.toList());
//
//        // Creating and returning OrderResponse
//        OrderResponse response = new OrderResponse();
//        response.setContent(collect);
//        response.setPageNumber(findAll.getNumber());
//        response.setLastPage(findAll.isLast());
//        response.setPageSize(findAll.getSize());
//        response.setTotalPage(findAll.getTotalPages());
//        response.setTotalElement(findAll.getTotalElements()); // Fixed typo here
//
//        return response;
//    }
    private void sendOrderConfirmationEmail(Order order, String userEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Order Confirmation");

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Thank you for your order!\n\nOrder Details:\n");
        emailContent.append("Order ID: ").append(order.getId()).append("\n");
        emailContent.append("Total Amount: $").append(order.getOrderAmt()).append("\n");

        // Include order items
        Set<OrderItem> orderItems = order.getOrderItem();
        for (OrderItem item : orderItems) {
            emailContent.append("Product: ").append(item.getProduct().getName()).append("\n");
            emailContent.append("Quantity: ").append(item.getProductQuantity()).append("\n");
            emailContent.append("Total Price: $").append(item.getTotalProductPrice()).append("\n");
            emailContent.append("Thank you for your visit\n");
        }

        mailMessage.setText(emailContent.toString());

        // Send the email
        javaMailSender.send(mailMessage);
    }
}

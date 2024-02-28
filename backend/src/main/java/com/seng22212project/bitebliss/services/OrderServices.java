//package com.seng22212project.bitebliss.services;
//import com.seng22212project.bitebliss.dtos.*;
//import com.seng22212project.bitebliss.models.*;
//import com.seng22212project.bitebliss.payload.OrderResponse;
//import com.seng22212project.bitebliss.repositories.*;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.modelmapper.ModelMapper;
//import javax.persistence.EntityNotFoundException;
//
//import jakarta.persistence.*;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import java.awt.print.Pageable;
//import java.util.List;
//import java.util.MissingResourceException;
//import java.util.Set;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//
//
//@Service
//public class OrderServices {
//    @Autowired
//  private  UserRepository userRepo;
//    @Autowired
//    private cartRepository cartRepo;
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private OrderRepository orderRepo;
//    //order Create method
//    public OrderDto orderCreate(OrderRequest request, String username){
//        User user= this.userRepo.findByEmail(username).orElseThrow(()->new MissingResourceException("User not found"));
//        int cartId=request.getCartId();
//        String addressLine1= request.getAddressLine1();
//        String addressLine2= request.getAddressLine2();
//        String city= request.getCity();
//        String district=request.getDistrict();
//
//        Cart cart= this.cartRepo.findById(cartId).orElseThrow(()->new MissingResourceException("Cart not Found"));
//
//        Set<CartItem> items=cart.getItems();
//
//        Order order=new Order();
//        AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);
//        Set<OrderItem> orderItems=items.stream().map((cartItem )-> {
//            OrderItem orderItem=new OrderItem();
//            //set cartItem into orderItem
//
//            //set productqty in orderItem
//            orderItem.setProduct(cartItem.getProduct());
//        //set productQty in orderItem
//            orderItem.setProductQuantity(cartItem.getQuantity());
//            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
//            orderItem.setOrder(order);
//
//            totalOrderPrice.set(totalOrderPrice.get()+orderItem.getTotalProductPrice());
//            int productId=orderItem.getProduct().getProductId();
//            return orderItem;
//        }).collect(Collectors.toSet());
//
//        order.setAddressLine1(addressLine1);
//        order.setAddressLine2(addressLine2);
//        order.setCity(city);
//        order.setDistrict(district);
//        order.setOrderStatus("created");
//        order.setPaymentStatus("not paid");
//        order.setUserId(user);
//        order.setOrderItem(orderItems);
//        order.setOrderAmt(totalOrderPrice.get());
//        Order savedOrder;
//        if(totalOrderPrice.get()>0){
//            savedOrder = this.orderRepo.save(order);
//            cart.getItems().clear();
//            Cart savedCart=this.cartRepo.save(cart);
//            System.out.println("Hello");
//
//        }else {
//            System.out.println(order.getOrderAmt());
//            throw  new MissingResourceException("plz Add Cart First and place order");
//        }
//
//
//       return this.modelMapper.map(savedOrder,cartDto.class);
//        //return this.modelMapper.map(order, OrderDto.class);
//
//    }
//    public void cancelOrder(int orderId){
//       Order order= this.orderRepo.findById(orderId).orElseThrow(()->new MissingResourceException("Order not found"));
//        this.orderRepo.delete(order);
//    }
//
//    public OrderDto findById(int orderId){
//        Order order=this.orderRepo.findById(orderId)
//                .orElseThrow(()-> EntityNotFoundException("Order not found"));
//
//        return this.modelMapper.map(order,OrderDto.class);
//    }
//    public OrderResponse findAllOrders(int pageNumber,int pageSize){
//        Pageable pageable=PageRequest.of(pageNumber,pageSize);
//        Page<Order> findAll=this.orderRepo.findAll(pageable);
//        List<Order> content=findAll.getContent();
//
//        List<OrderDto> collect = content.stream().map(each -> this.modelMapper.map(each, OrderDto.class)).collect(Collectors.toList());
//        OrderResponse response = new OrderResponse();
//        response.setContent(collect);
//        response.setPageNumber(findAll.getNumber());
//        response.setLastPage(findAll.isLast());
//        response.setPageSize(findAll.getSize());
//        response.setTotalPage(findAll.getTotalPages());
//        response.setTotalElement(findAll.getTotalElements());
//        return response;
//    }
//
//}
package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.*;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.payload.OrderResponse;
import com.seng22212project.bitebliss.repositories.*;

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

    // Order Create method
    public OrderDto orderCreate(OrderRequest request, String username) {
        // Retrieving user information
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new MissingResourceException("User not found"));

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

        return this.modelMapper.map(savedOrder, OrderDto.class);
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
    public OrderResponse findAllOrders(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> findAll = this.orderRepo.findAll(pageable);
        List<Order> content = findAll.getContent();

        // Mapping Order objects to OrderDto
        List<OrderDto> collect = content.stream().map(each -> this.modelMapper.map(each, OrderDto.class)).collect(Collectors.toList());

        // Creating and returning OrderResponse
        OrderResponse response = new OrderResponse();
        response.setContent(collect);
        response.setPageNumber(findAll.getNumber());
        response.setLastPage(findAll.isLast());
        response.setPageSize(findAll.getSize());
        response.setTotalPage(findAll.getTotalPages());
        response.setTotalElement(findAll.getTotalElements()); // Fixed typo here

        return response;
    }
}

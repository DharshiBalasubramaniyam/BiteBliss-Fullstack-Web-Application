package com.seng22212project.bitebliss.services;
import com.seng22212project.bitebliss.dtos.*;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import javax.persistence.EntityNotFoundException;

import jakarta.persistence.*;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class OrderServices {
    @Autowired
  private  UserRepository userRepo;
    @Autowired
    private cartRepository cartRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepo;
    //order Create method
    public OrderDto orderCreate(OrderRequest request, String username){
        User user= this.userRepo.findByEmail(username).orElseThrow(()->new MissingResourceException("User not found"));
        int cartId=request.getCartId();
        String addressLine1= request.getAddressLine1();
        String addressLine2= request.getAddressLine2();
        String city= request.getCity();
        String district=request.getDistrict();

        Cart cart= this.cartRepo.findById(cartId).orElseThrow(()->new MissingResourceException("Cart not Found"));

        Set<CartItem> items=cart.getItems();

        Order order=new Order();
        AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);
        Set<OrderItem> orderItems=items.stream().map((cartItem )-> {
            OrderItem orderItem=new OrderItem();
            //set cartItem into orderItem

            //set productqty in orderItem
            orderItem.setProduct(cartItem.getProduct());
        //set productQty in orderItem
            orderItem.setProductQuantity(cartItem.getQuantity());
            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);

            totalOrderPrice.set(totalOrderPrice.get()+orderItem.getTotalProductPrice());
            int productId=orderItem.getProduct().getProductId();
            return orderItem;
        }).collect(Collectors.toSet());

        order.setAddressLine1(addressLine1);
        order.setAddressLine2(addressLine2);
        order.setCity(city);
        order.setDistrict(district);
        order.setOrderStatus("created");
        order.setPaymentStatus("not paid");
        order.setUserId(user);
        order.setOrderItem(orderItems);
        order.setOrderAmt(totalOrderPrice.get());
        Order savedOrder;
        if(totalOrderPrice.get()>0){
            savedOrder = this.orderRepo.save(order);
            cart.getItems().clear();
            Cart savedCart=this.cartRepo.save(cart);
            System.out.println("Hello");

        }else {
            System.out.println(order.getOrderAmt());
            throw  new MissingResourceException("plz Add Cart First and place order");
        }


       return this.modelMapper.map(savedOrder,cartDto.class);
        //return this.modelMapper.map(order, OrderDto.class);

    }
    public void cancelOrder(int orderId){
       Order order= this.orderRepo.findById(orderId).orElseThrow(()->new MissingResourceException("Order not found"));
        this.orderRepo.delete(order);
    }

    public OrderDto findById(int orderId){
        Order order=this.orderRepo.findById(orderId)
                .orElseThrow(()-> EntityNotFoundException("Order not found"));

        return this.modelMapper.map(order,OrderDto.class);
    }
    
}

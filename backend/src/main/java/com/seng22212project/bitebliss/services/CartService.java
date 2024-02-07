package com.seng22212project.bitebliss.dtos;

import com.seng22212project.bitebliss.dtos.CartDto;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.payload.ItemRequest;
import com.seng22212project.bitebliss.repositories.*;
import com.seng22212project.bitebliss.Exception.ResourceNotFoundException;


import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CartService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProductRepository productRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    public CartDto addItem(ItemRequest item, String Username) {
        int productId = item.getProductId();
        int quantity = item.getQuantity();
        User user = (User) this.userRepo.findByEmail(Username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

//        Checking the product stock
        if(!product.isStock()){
            throw new ResourceNotFoundException("Product out of stock");
        }

//        Create cartItem with productId and quantity
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        double totalPrice = product.getPrice()*product.getQuantity();
        cartItem.setTotalPrice(totalPrice);

//        getting cart from user
        Cart cart = user.getCart();

        if(cart == null) {
            Cart cart1 = new Cart();
            user.setCart(cart1);
        }

        cartItem.setCart(cart);

        Set<CartItem> items = cart.getItems();
        AtomicReference<Boolean> flag = new AtomicReference<>();
        Set<CartItem> newproduct = items.stream().map((i)->{
            if(i.getProduct().getProduct_id() == product.getProduct_id()){
                i.setQuantity(quantity);
                i.setTotalPrice(totalPrice);
                flag.set(true);
            }
            return i;
        }).collect(Collectors.toSet());

        if(flag.get()){
            items.clear();
            items.addAll(newproduct);
        }else{
            cartItem.setCart(cart);
            items.add(cartItem);
        }

        Cart saveCart = this.cartRepo.save(cart);

        return this.modelMapper.map(saveCart,CartDto.class);
    }
}

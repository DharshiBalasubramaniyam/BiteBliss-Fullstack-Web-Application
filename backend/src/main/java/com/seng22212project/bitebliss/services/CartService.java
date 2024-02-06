package com.seng22212project.bitebliss.dtos;

import com.seng22212project.bitebliss.dtos.CartDto;
import com.seng22212project.bitebliss.models.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.seng22212project.bitebliss.Exception.ResourceNotFoundException;
import com.seng22212project.bitebliss.payload.ItemRequest;
import com.seng22212project.bitebliss.repositories.*;

public class CartService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProductRepository productRepo;

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

        return null;
    }
}

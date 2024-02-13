package com.seng22212project.bitebliss.dtos;

import com.seng22212project.bitebliss.dtos.CartDto;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.payload.ItemRequest;
import com.seng22212project.bitebliss.repositories.*;
import com.seng22212project.bitebliss.Exception.ResourceNotFoundException;
import java.util.Optional;


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

    public CartDto addItem(ItemRequest item, String email) {
        int productId = item.getProductId();
        int quantity = item.getQuantity();
        User user =  this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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

    public CartDto getCartItems(String email){
        User user =  this.userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Cart cart = this.cartRepo.findCartByUser(user).orElseThrow(()->new ResourceNotFoundException("There is no cart"));
        return this.modelMapper.map(cart,CartDto.class);
    }

    //get cart by CartId
//    public CartDto getCartById(long cartId){
//        Cart findByUserAndCartId = this.cartRepo.findByUserAndCartId(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not found"));
//        return this.modelMapper.map(findByUserAndCartId,CartDto.class);
//    }

    public CartDto removeCartItemFromCart(String email,int ProductId){
        User user = this.userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();
        Set<CartItem> items = cart.getItems();
        boolean removeIf = items.removeIf((i)->i.getProduct().getProduct_id()==ProductId);
        Cart save = this.cartRepo.save(cart);
        return this.modelMapper.map(save,CartDto.class);
    }

    public CartDto updateCartItem(ItemRequest itemRequest, String email) {
        int productId = itemRequest.getProductId();
        int quantity = itemRequest.getQuantity();

        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();

        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        Set<CartItem> items = cart.getItems();

        Optional<CartItem> existingCartItemOptional = items.stream()
                .filter(cartItem -> cartItem.getProduct().getProduct_id() == productId)
                .findFirst();

        if (existingCartItemOptional.isPresent()) {
            CartItem existingCartItem = existingCartItemOptional.get();
            Product product = existingCartItem.getProduct();

            if (!product.isStock()) {
                throw new ResourceNotFoundException("Product out of stock");
            }

            existingCartItem.setQuantity(quantity);
            double totalPrice = Double.parseDouble(product.getPrice()) * quantity;
            existingCartItem.setTotalPrice(totalPrice);

            Cart saveCart = this.cartRepo.save(cart);
            return this.modelMapper.map(saveCart, CartDto.class);
        } else {
            throw new ResourceNotFoundException("CartItem not found");
        }
    }
}

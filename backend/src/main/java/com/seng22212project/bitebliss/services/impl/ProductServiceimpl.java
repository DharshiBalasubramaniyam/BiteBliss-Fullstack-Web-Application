package com.seng22212project.bitebliss.services.impl;

import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceimpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Products create(Products products){
        return productRepository.save(products);
    }

}

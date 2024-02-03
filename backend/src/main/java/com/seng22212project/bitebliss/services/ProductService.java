package com.seng22212project.bitebliss.services;

import com.example.demo.Model.Products;
        import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public Products create(Products products);
}
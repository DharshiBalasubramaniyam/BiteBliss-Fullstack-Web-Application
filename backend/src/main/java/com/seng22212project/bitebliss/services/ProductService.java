package com.seng22212project.bitebliss.services;

import com.example.demo.dtos.ProductDto;
import com.example.demo.models.Products;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    public ProductDto create(ProductDto products);

    public List<ProductDto> viewAllProducts();

    public ProductDto viewProductById(int pid);

    public Products toEntity(ProductDto productDto);
    public ProductDto toDto(Products products);

}

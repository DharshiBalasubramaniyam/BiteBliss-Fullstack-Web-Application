package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.responses.ProductDto;
import com.seng22212project.bitebliss.models.Products;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public ProductDto create(ProductDto products, int cid);

   public List<ProductDto> viewAllProducts();

   public ProductDto viewProductById(int pid);

    public List<ProductDto> getProductByCategory(String categoryName);

    public ProductDto updateProduct(int pid, ProductDto updatedProductDto);


    public List<ProductDto> searchProducts(String searchKey);
    public Products toEntity(ProductDto productDto);
   public ProductDto toDto(Products products);



}

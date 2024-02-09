package com.seng22212project.bitebliss.services.impl;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.dtos.ProductDto;
import com.example.demo.models.Category;
import com.example.demo.models.Products;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceimpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public ProductDto create(ProductDto products) {

        Products entity=toEntity(products);
        Products save=productRepository.save(entity);
        ProductDto dto=toDto(save);
        return dto;

    }



    @Override
    public List<ProductDto> viewAllProducts(){
        List<Products> findALL= productRepository.findAll();
        List<ProductDto> findAllDto= findALL.stream().map(products -> this.toDto(products)).collect(Collectors.toList());
        return findAllDto;
    }

    @Override
    public ProductDto viewProductById(int pid)
    {
        Products findById=productRepository.findById(pid).orElseThrow(()->new ResourceNotFoundException("product with this product id " +pid+" is not found"));
        ProductDto productDto = this.toDto(findById);
        return productDto;
    }

    //productDto to product
    @Override
    public Products toEntity(ProductDto pDto) {
        Products p=new Products();
        p.setProduct_name(pDto.getProduct_name());
        p.setProduct_id(pDto.getProduct_id());
        p.setDescription(pDto.getDescription());
        p.setPrice(pDto.getPrice());
        p.setImageUrl(pDto.getImageUrl());
        return p;
    }

    //product to productDto
    @Override
    public ProductDto toDto(Products products) {
        ProductDto productDto=new ProductDto();

        productDto.setProduct_id(products.getProduct_id());
        productDto.setProduct_name(products.getProduct_name());
        productDto.setDescription(products.getDescription());
        productDto.setPrice(products.getPrice());
        productDto.setImageUrl(products.getImageUrl());
        return productDto;
    }





}

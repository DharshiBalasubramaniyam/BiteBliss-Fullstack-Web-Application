package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.responses.CategoryDto;
import com.seng22212project.bitebliss.dtos.responses.ProductDto;
import com.seng22212project.bitebliss.exceptions.CategoryNotFoundException;
import com.seng22212project.bitebliss.exceptions.ProductNotFoundException;
import com.seng22212project.bitebliss.models.Category;
import com.seng22212project.bitebliss.models.Products;
import com.seng22212project.bitebliss.repositories.CategoryRepository;
import com.seng22212project.bitebliss.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceimpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public ProductDto create(ProductDto productDto, int cid) {

        Category cat = this.categoryRepository.findById(cid).orElseThrow(() -> new CategoryNotFoundException("the category with this category id cannot be found"));

        Products product = toEntity(productDto);
        product.setCategory(cat);
        Products save = this.productRepository.save(product);
        ProductDto dto = toDto(save);
        return dto;

    }


    @Override
    public List<ProductDto> viewAllProducts() {
        List<Products> findALL = productRepository.findAll();
        List<ProductDto> findAllDto = findALL.stream().map(this::toDto).collect(Collectors.toList());
        return findAllDto;
    }

    @Override
    public ProductDto viewProductById(int pid) {
        Products findById = productRepository.findById(pid).orElseThrow(() -> new ProductNotFoundException("product with this product id " + pid + " is not found"));
        ProductDto productDto = this.toDto(findById);
        return productDto;
    }



    @Override
    public List<ProductDto> getProductByCategory(String categoryName) {

        List<Products> productsInCategory = this.productRepository.findByCategory_CategoryNameContaining(categoryName);

//        if (productsInCategory.isEmpty()) {
//            throw new ProductNotFoundException("No products found for category with name containing: " + categoryName);
//        }

        return productsInCategory.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public ProductDto updateProduct(int pid, ProductDto updatedProductDto) {

        Products existingProduct = productRepository.findById(pid)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + pid));


        existingProduct.setProduct_name(updatedProductDto.getProduct_name());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setImageUrl(updatedProductDto.getImageUrl());

        Products updatedProduct = this.productRepository.save(existingProduct);

        return toDto(updatedProduct);
    }


    @Override
    public List<ProductDto> searchProducts(String partialProductName) {
        List<Products> productsByName = productRepository.findByProductNameContaining(partialProductName);

//        if (productsByName.isEmpty()) {
//            throw new ProductNotFoundException("No products found with name containing: " + partialProductName);
//        }

        List<ProductDto> productDtos = productsByName.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return productDtos;
    }


    //productDto to product
    @Override
    public Products toEntity(ProductDto pDto) {
        Products p = new Products();
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
        ProductDto productDto = new ProductDto();

        productDto.setProduct_id(products.getProduct_id());
        productDto.setProduct_name(products.getProduct_name());
        productDto.setDescription(products.getDescription());
        productDto.setPrice(products.getPrice());
        productDto.setImageUrl(products.getImageUrl());

        //change category to categoryDto

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategory_id(products.getCategory().getCategory_id());
        categoryDto.setCategory_name(products.getCategory().getCategory_name());

        //setting categoryDto in productDto
        productDto.setCategoryDto(categoryDto);
        return productDto;
    }
}


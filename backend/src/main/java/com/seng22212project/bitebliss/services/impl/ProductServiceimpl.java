package com.seng22212project.bitebliss.services.impl;

import com.example.demo.dtos.CategoryDto;
import com.example.demo.dtos.ProductDto;
import com.example.demo.exceptions.CategoryNotFoundException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.models.Products;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;
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
    public ProductDto create(ProductDto productDto,int cid) {

        Category cat =this.categoryRepository.findById(cid).orElseThrow(()->new CategoryNotFoundException("the category with this category id cannot be found"));

        Products product=toEntity(productDto);
        product.setCategory(cat);
        Products save=this.productRepository.save(product);
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
        Products findById=productRepository.findById(pid).orElseThrow(()->new ProductNotFoundException("product with this product id " +pid+" is not found"));
        ProductDto productDto = this.toDto(findById);
        return productDto;
    }

    @Override
    public List<ProductDto> getProductByCategory(int cid) {
        Category cat=this.categoryRepository.findById(cid).orElseThrow(()->new CategoryNotFoundException("the category with this category id cannot be found"));
        List<ProductDto> findByCategory=this.productRepository.findByCategory(cat).stream().map(products -> toDto(products)).collect(Collectors.toList());
        return findByCategory;
    }

    @Override
    public ProductDto updateProduct(int pid, ProductDto updatedProductDto) {

            Products existingProduct = productRepository.findById(pid)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + pid));


            existingProduct.setProduct_name(updatedProductDto.getProduct_name());
            existingProduct.setPrice(updatedProductDto.getPrice());
            existingProduct.setDescription(updatedProductDto.getDescription());
            existingProduct.setImageUrl(updatedProductDto.getImageUrl());



            Products updatedProduct =this.productRepository.save(existingProduct);


            return toDto(updatedProduct);
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

       //change categoty to categoryDto

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategory_id(products.getCategory().getCategory_id());
        categoryDto.setCategory_name(products.getCategory().getCategory_name());

        //setting categoryDto in productDto
        productDto.setCategoryDto(categoryDto);
        return productDto;
    }





}

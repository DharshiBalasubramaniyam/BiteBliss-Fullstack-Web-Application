
package com.seng22212project.bitebliss.controllers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.models.Products;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/store")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto product){
        ProductDto saveProduct= productService.create(product);
        return new ResponseEntity<ProductDto>(saveProduct, HttpStatus.CREATED);

    }

    @GetMapping("/view")
    public ResponseEntity<List<ProductDto>>viewAllProducts(){
        List<ProductDto> viewAll=productService.viewAllProducts();
        return new ResponseEntity<List<ProductDto>>(viewAll,HttpStatus.ACCEPTED);

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ProductDto> viewProductById(@PathVariable int id)
    {
        ProductDto viewById=productService.viewProductById(id);
        return new ResponseEntity<ProductDto>(viewById,HttpStatus.OK);


    }
}

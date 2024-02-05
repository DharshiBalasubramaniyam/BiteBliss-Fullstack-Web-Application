package com.seng22212project.bitebliss.Controller;

import com.example.demo.Model.Products;
        import com.example.demo.Service.ProductService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;
//
//    @GetMapping("/products")
//    public String index(){
//        return ("helloooo");
//    }

    @PostMapping("/store")
    public Products saveProduct(@RequestBody Products product){
        return productService.create(product);

    }
}

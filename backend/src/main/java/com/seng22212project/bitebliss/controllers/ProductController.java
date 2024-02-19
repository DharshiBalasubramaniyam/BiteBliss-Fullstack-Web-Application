
package com.seng22212project.bitebliss.controllers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.services.ProductService;
import com.example.demo.services.fileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bitebliss/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    fileUpload fileupload;
    @Value("${product.path.images}")
    private String imagePath;



    @PostMapping("images/{pid}")
    public ResponseEntity<?> uploadImageOfProduct(@PathVariable int pid, @RequestParam ("product_image") MultipartFile file){
        ProductDto product=this.productService.viewProductById(pid);

        String imageName=null;

        try{
            String uploadImage=this.fileupload.uploadImage(imagePath,file);
            product.setImageUrl(uploadImage);

            ProductDto updateProduct=this.productService.updateProduct(pid,product);
            return new ResponseEntity<>(updateProduct,HttpStatus.ACCEPTED);

        }catch (Exception e){
            e.printStackTrace();
             return new ResponseEntity<>(Map.of("Message","File not upload in server"),HttpStatus.INTERNAL_SERVER_ERROR);
//           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }

    }

    @PostMapping("/store/{cid}")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto product,@PathVariable int cid){
       ProductDto saveProduct= productService.create(product,cid);
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

    @GetMapping("/category/{cid}")
    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable int cid){
        List<ProductDto> getProductByCat=this.productService.getProductByCategory(cid);

        return new ResponseEntity<List<ProductDto>>(getProductByCat,HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int productId, @RequestBody ProductDto updatedProductDto) {
        // Implement the logic to update the product
        ProductDto updated = productService.updateProduct(productId,updatedProductDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}

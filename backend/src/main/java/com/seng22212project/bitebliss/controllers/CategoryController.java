package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.responses.CategoryDto;
import com.seng22212project.bitebliss.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bitebliss/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/storeCategory")
    public ResponseEntity<CategoryDto> saveProduct(@RequestBody CategoryDto categoryDto){
        CategoryDto saveCategory= categoryService.create(categoryDto);
        return new ResponseEntity<CategoryDto>(saveCategory, HttpStatus.CREATED);

    }

    @GetMapping("/viewCategory")
    public ResponseEntity<List<CategoryDto>> viewAllCategories(){

            List<CategoryDto> viewAll=categoryService.viewAllCategories();
            return new ResponseEntity<List<CategoryDto>>(viewAll,HttpStatus.ACCEPTED);

    }

    @GetMapping("/viewCategory/{id}")
    public ResponseEntity<CategoryDto> viewCategoryById(@PathVariable int cid){
        {
            CategoryDto viewById=categoryService.viewCategoryById(cid);
            return new ResponseEntity<CategoryDto>(viewById,HttpStatus.OK);

        }
    }


}

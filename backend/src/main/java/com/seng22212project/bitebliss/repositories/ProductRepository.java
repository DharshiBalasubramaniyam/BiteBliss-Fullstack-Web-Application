package com.seng22212project.bitebliss.repositories;


import com.seng22212project.bitebliss.models.Category;
import com.seng22212project.bitebliss.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Integer> {

    List<Products> findByCategory(Category category);
    List<Products> findByProductNameContaining(String partialProductName);
    List<Products> findByCategory_CategoryNameContaining( String categoryName);



}

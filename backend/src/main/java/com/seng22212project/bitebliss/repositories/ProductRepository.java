package com.seng22212project.bitebliss.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Integer> {

    List<Products> findByCategory(Category category);

}

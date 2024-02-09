package com.seng22212project.bitebliss.repositories;


import com.example.demo.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface ProductRepository extends JpaRepository<Products,Integer> {
    }


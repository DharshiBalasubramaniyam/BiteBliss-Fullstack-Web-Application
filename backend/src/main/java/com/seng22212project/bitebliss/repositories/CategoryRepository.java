package com.seng22212project.bitebliss.repositories;


import com.seng22212project.bitebliss.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findByCategoryNameContaining(String categoryName);
}

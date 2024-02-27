package com.seng22212project.bitebliss.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Integer> {

    List<Products> findByCategory(Category category);
    List<Products> findByProductNameContaining(String partialProductName);
    List<Products> findByCategory_CategoryNameContaining( String categoryName);

//    @Query("SELECT p FROM Products p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :searchKey, '%')) GROUP BY p.productName, p.category.categoryName")
//    List<Products> findDistinctByProductNameOrCategoryName(@Param("searchKey") String searchKey);


}

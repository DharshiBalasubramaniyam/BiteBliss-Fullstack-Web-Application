package com.seng22212project.bitebliss.services;

import com.example.demo.dtos.CategoryDto;
import com.example.demo.models.Category;

import java.util.List;

public interface CategoryService {

    public CategoryDto create(CategoryDto categoryDto);

    public List<CategoryDto> viewAllCategories();


    public CategoryDto viewCategoryById(int cid);

    public Category toEntity(CategoryDto categoryDto);
    public CategoryDto toDto(Category category);
}

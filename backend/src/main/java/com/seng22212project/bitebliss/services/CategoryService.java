package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.responses.CategoryDto;
import com.seng22212project.bitebliss.models.Category;

import java.util.List;

public interface CategoryService {

    public CategoryDto create(CategoryDto categoryDto);

    public List<CategoryDto> viewAllCategories();


    public CategoryDto viewCategoryById(int cid);

    public Category toEntity(CategoryDto categoryDto);
    public CategoryDto toDto(Category category);
}

package com.seng22212project.bitebliss.services.impl;

import com.example.demo.dtos.CategoryDto;
import com.example.demo.exceptions.CategoryNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {


        Category entity = toEntity(categoryDto);
        Category save = categoryRepository.save(entity);
        CategoryDto dto = toDto(save);
        return dto;
    }

    @Override
    public List<CategoryDto> viewAllCategories() {
        List<Category> findProductsByCategory = categoryRepository.findAll();

        if (findProductsByCategory.isEmpty()) {
            throw new CategoryNotFoundException("No category found with name containing: " + findProductsByCategory);
        }

        List<CategoryDto> findAllDto = findProductsByCategory.stream().map(category -> this.toDto(category)).collect(Collectors.toList());
        return findAllDto;
    }



    @Override
    public CategoryDto viewCategoryById(int cid) {
        Category findById = categoryRepository.findById(cid).orElseThrow(() -> new CategoryNotFoundException("category with this category id " + cid + " is not found"));
        CategoryDto categoryDto = this.toDto(findById);
        return categoryDto;
    }

    @Override
    public Category toEntity(CategoryDto cDto) {
        Category c = new Category();

        c.setCategory_id(cDto.getCategory_id());
        c.setCategory_name(cDto.getCategory_name());
        return c;
    }


    @Override
    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategory_id(category.getCategory_id());
        categoryDto.setCategory_name(category.getCategory_name());
        return categoryDto;

    }
}

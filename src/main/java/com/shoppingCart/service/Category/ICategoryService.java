package com.shoppingCart.service.Category;

import com.shoppingCart.DTOs.CategoryDTO;
import com.shoppingCart.model.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDTO addCategory(CategoryDTO category);
    CategoryDTO updateCategory(CategoryDTO category, Long categoryId);
    void deleteCategory(Long categoryId);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long categoryId);
    CategoryDTO getCategoryByName(String name);
}

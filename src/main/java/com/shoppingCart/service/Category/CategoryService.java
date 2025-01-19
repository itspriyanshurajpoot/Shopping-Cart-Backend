package com.shoppingCart.service.Category;

import com.shoppingCart.DTOs.CategoryDTO;
import com.shoppingCart.exception.AlreadyExistException;
import com.shoppingCart.exception.ResourceNotFoundException;
import com.shoppingCart.model.Category;
import com.shoppingCart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    // category repository object
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO addCategory(CategoryDTO category) {
        Category category1 = dtoToCategory(category);
        Category category2 = Optional.ofNullable(category1).filter(c -> !categoryRepository.existsByName(category.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistException("Category already exist"));
        return categoryToDto(category2);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO category, Long categoryId) {
        Category category1 = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));

        category1.setName(category.getName());
        return categoryToDto(categoryRepository.save(category1));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {throw new ResourceNotFoundException("Category doesn't exist");});
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDTO> categoryDTOS = categories.stream().map(c -> categoryToDto(c)).toList();
        return categoryDTOS;
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));
        return categoryToDto(category);
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = Optional.ofNullable(categoryRepository.findByName(name))
                .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));
        return categoryToDto(category);
    }

    // category to dto
    public CategoryDTO categoryToDto(Category category){
        return modelMapper.map(category, CategoryDTO.class);
    }

    // dto to category
    public Category dtoToCategory(CategoryDTO dto){
        return modelMapper.map(dto, Category.class);
    }
}

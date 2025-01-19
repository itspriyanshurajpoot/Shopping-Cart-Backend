package com.shoppingCart.controllers;

import com.shoppingCart.DTOs.CategoryDTO;
import com.shoppingCart.exception.ResourceNotFoundException;
import com.shoppingCart.repository.CategoryRepository;
import com.shoppingCart.response.ApiResponse;
import com.shoppingCart.service.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    // add category
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDTO categoryDTO) {

        try {
            return new ResponseEntity<>(new ApiResponse("Add success!", categoryService.addCategory(categoryDTO)), HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // update category
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

        try {
            return new ResponseEntity<>(new ApiResponse("Update success!", categoryService.updateCategory(categoryDTO, categoryId)), HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // delete category
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(new ApiResponse("Delete success!", null), HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    // get all category
    @GetMapping("/all-category")
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAllCategories();
    }

    //get category by id
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success!", categoryService.getCategoryById(categoryId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // get category by name
    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getCategoryByName (@RequestParam String categoryName){
        try {
            return ResponseEntity.ok(new ApiResponse("Success!", categoryService.getCategoryByName(categoryName)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}

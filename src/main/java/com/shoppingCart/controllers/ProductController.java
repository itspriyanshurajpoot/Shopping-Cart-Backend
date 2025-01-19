package com.shoppingCart.controllers;

import com.shoppingCart.DTOs.ProductDTO;
import com.shoppingCart.exception.ProductNotFoundException;
import com.shoppingCart.response.ApiResponse;
import com.shoppingCart.service.Product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    // product service
    private final IProductService productService;

    // add product
    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO dto){
        try{
            return ResponseEntity.ok(new ApiResponse("Added!", productService.addProduct(dto)));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // update product
    @PutMapping("/update/product-id/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDTO dto, @PathVariable Long productId){
        try{
            return ResponseEntity.ok(new ApiResponse("Updated!", productService.updateProduct(dto, productId)));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // delete product
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try{
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Deleted!", null));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // get product by id
    @GetMapping("/product-id/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try{
            return ResponseEntity.ok(new ApiResponse("Updated!", productService.getProductById(productId)));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // get all product
    @GetMapping("/all-products")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        try{
            return ResponseEntity.ok(productService.getAllProduct());
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // get all product by brand
    @GetMapping("/brand/{brandName}")
    public ResponseEntity<List<ProductDTO>> getAllProductByBrand(@PathVariable String brandName){
        try{
            return ResponseEntity.ok(productService.getAllProductByBrand(brandName.toUpperCase()));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }


    // get all product by category
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getAllProductByCategory(@PathVariable String categoryName){
        try{
            return ResponseEntity.ok(productService.getAllProductByCategoryName(categoryName));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }


    // get all product by category and brand
    @GetMapping("/category-name/{categoryName}/brand-name/{brandName}")
    public ResponseEntity<List<ProductDTO>> getAllProductByCategoryAndBrand( @PathVariable String categoryName,@PathVariable String brandName){
        try{
            return ResponseEntity.ok(productService.getAllProductByCategoryNameAndBrand(categoryName, brandName.toUpperCase()));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }

    // get all product by name
    @GetMapping("/product-name/{productName}")
    public ResponseEntity<List<ProductDTO>> getAllProductByName(@PathVariable String productName){
        try{
            return ResponseEntity.ok(productService.getProductByName(productName));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
    // get all product by brand and name
    @GetMapping("/brand-name/{brandName}/product-name/{productName}")
    public ResponseEntity<List<ProductDTO>> getAllProductByBrandAndName( @PathVariable String productName,@PathVariable String brandName){
        try{
            return ResponseEntity.ok(productService.getProductByBrandAndName(brandName.toUpperCase(), productName));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
    // count all product by brand and name
    @GetMapping("/coun/product-name/{productName}/brand-name/{brandName}")
    public ResponseEntity<String> countProductsByBrandAndName(@PathVariable String brandName,@PathVariable String productName){
        try{
            return ResponseEntity.ok("Total number of products are : " + productService.countProductByBrandAndName(brandName, productName));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
}

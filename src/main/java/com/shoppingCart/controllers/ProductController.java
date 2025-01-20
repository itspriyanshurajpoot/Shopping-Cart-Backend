package com.shoppingCart.controllers;

import com.shoppingCart.DTOs.ProductDTO;
import com.shoppingCart.exception.ProductNotFoundException;
import com.shoppingCart.response.ApiListResponse;
import com.shoppingCart.response.ApiResponse;
import com.shoppingCart.service.Product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO dto) {
        try {
            return ResponseEntity.ok(new ApiResponse("Added!", productService.addProduct(dto)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // update product
    @PutMapping("/update/product-id/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDTO dto, @PathVariable Long productId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Updated!", productService.updateProduct(dto, productId)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // delete product
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse("Deleted!", null));
    }

    // get product by id
    @GetMapping("/product-id/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {

        return ResponseEntity.ok(new ApiResponse("Updated!", productService.getProductById(productId)));

    }

    // get all product
    @GetMapping("/all-products")
    public ResponseEntity<ApiListResponse> getAllProduct() {
        try {
            List<ProductDTO> allProduct = productService.getAllProduct();
            if (allProduct.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(new ApiListResponse("Found!", Collections.singletonList(allProduct)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiListResponse(e.getMessage(), null));
        }
    }

    // get all product by brand
    @GetMapping("/brand/{brandName}")
    public ResponseEntity<ApiListResponse> getAllProductByBrand(@PathVariable String brandName) {
        try {
            List<ProductDTO> allProductByBrand = productService.getAllProductByBrand(brandName.toUpperCase());
            if (allProductByBrand.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(new ApiListResponse("Found!", Collections.singletonList(allProductByBrand)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiListResponse(e.getMessage(), null));
        }
    }


    // get all product by category
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiListResponse> getAllProductByCategory(@PathVariable String categoryName) {

        try {
            List<ProductDTO> allProductByCategoryName = productService.getAllProductByCategoryName(categoryName);
            if (allProductByCategoryName.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(new ApiListResponse("Found!", Collections.singletonList(allProductByCategoryName)));
        }  catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiListResponse(e.getMessage(), null));
        }
    }


    // get all product by category and brand
    @GetMapping("/category-name/{categoryName}/brand-name/{brandName}")
    public ResponseEntity<ApiListResponse> getAllProductByCategoryAndBrand(@PathVariable String categoryName, @PathVariable String brandName) {
        try {
            List<ProductDTO> allProducts = productService.getAllProductByCategoryNameAndBrand(categoryName, brandName.toUpperCase());
            if (allProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(new ApiListResponse("Found!", Collections.singletonList(allProducts)));
        }  catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiListResponse(e.getMessage(), null));
        }
    }

    // get all product by name
    @GetMapping("/product-name/{productName}")
    public ResponseEntity<ApiListResponse> getAllProductByName(@PathVariable String productName) {
        try {
            List<ProductDTO> productByName = productService.getProductByName(productName);
            if (productByName.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(new ApiListResponse("Found!", Collections.singletonList(productByName)));
        }  catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiListResponse(e.getMessage(), null));
        }
    }

    // get all product by brand and name
    @GetMapping("/brand-name/{brandName}/product-name/{productName}")
    public ResponseEntity<ApiListResponse> getAllProductByBrandAndName(@PathVariable String productName, @PathVariable String brandName) {
        try {
            List<ProductDTO> allProducts = productService.getProductByBrandAndName(brandName.toUpperCase(), productName);
            if (allProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(new ApiListResponse("Found!", Collections.singletonList(allProducts)));
        }  catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiListResponse(e.getMessage(), null));
        }
    }

    // count all product by brand and name
    @GetMapping("/coun/product-name/{productName}/brand-name/{brandName}")
    public ResponseEntity<String> countProductsByBrandAndName(@PathVariable String brandName, @PathVariable String productName) {
        try {
            return ResponseEntity.ok("Total number of products are : " + productService.countProductByBrandAndName(brandName, productName));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
}

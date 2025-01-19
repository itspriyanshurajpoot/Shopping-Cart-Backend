package com.shoppingCart.service.Product;

import com.shoppingCart.DTOs.ProductDTO;
import com.shoppingCart.model.Product;

import java.util.List;

public interface IProductService {

    // add product
    ProductDTO addProduct(ProductDTO dto);

    // delete product
    void deleteProduct(Long productId);

    // update product
    ProductDTO updateProduct(ProductDTO dto, long productId);

    // get product by product id
    ProductDTO getProductById(Long productId);

    // get all product
    List<ProductDTO>  getAllProduct();

    // get all product by brand
    List<ProductDTO>  getAllProductByBrand(String brand);

    // Get all product by category
    List<ProductDTO>  getAllProductByCategoryName(String category);

    // get all product by category and brand
    List<ProductDTO>  getAllProductByCategoryNameAndBrand(String category, String brand);

    // get All product by name
    List<ProductDTO> getProductByName(String name);

    // get product by brand and name;
    List<ProductDTO> getProductByBrandAndName(String brand, String name);

    // count product by brand and name
    Long countProductByBrandAndName(String brand, String name);
}

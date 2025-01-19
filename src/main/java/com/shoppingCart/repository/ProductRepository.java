package com.shoppingCart.repository;

import com.shoppingCart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrand(String brand);

    List<Product> findByCategoryName(String name);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);
    List<Product> findByCategoryNameAndBrand(String category, String brand);

    Long countByBrandAndName(String brand, String name);

}

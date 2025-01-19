package com.shoppingCart.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private CategoryDTO category;
}

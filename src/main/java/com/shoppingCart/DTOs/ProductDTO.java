package com.shoppingCart.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
    private List<ImageDTO> image;
}

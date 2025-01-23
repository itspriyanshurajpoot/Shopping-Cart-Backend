package com.shoppingCart.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInputDTO {
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String categoryName;
}

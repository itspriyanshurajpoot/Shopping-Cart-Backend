package com.shoppingCart.DTOs;

import lombok.*;

@Data
@RequiredArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String name;
}

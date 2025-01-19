package com.shoppingCart.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageDTO {

    private Long imageId;
    private String fileName;
    private String downloadUrl;
}

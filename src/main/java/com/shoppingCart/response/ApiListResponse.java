package com.shoppingCart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiListResponse {

    private String msg;
    private List<Object> objects;
}

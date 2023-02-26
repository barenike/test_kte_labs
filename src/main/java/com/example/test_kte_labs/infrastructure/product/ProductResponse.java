package com.example.test_kte_labs.infrastructure.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private Double price;
}

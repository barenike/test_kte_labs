package com.example.test_kte_labs.infrastructure.product;

import lombok.Data;

import java.util.Map;

@Data
public class ProductExtraInfoResponse {
    private String description;
    private Double averageRating;
    private Map<Integer, Integer> ratingMap;
}

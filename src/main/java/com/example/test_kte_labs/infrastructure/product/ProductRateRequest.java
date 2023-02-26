package com.example.test_kte_labs.infrastructure.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRateRequest {
    @NotNull
    private String clientId;

    @NotNull
    private String productId;

    private Integer rating;
}

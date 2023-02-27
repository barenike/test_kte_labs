package com.example.test_kte_labs.infrastructure.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ProductRateRequest {
    @NotNull
    private String clientId;
    @NotNull
    private String productId;
    @Range(min = 1, max = 5)
    private Integer rating;
}

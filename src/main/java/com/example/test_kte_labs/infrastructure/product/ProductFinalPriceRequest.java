package com.example.test_kte_labs.infrastructure.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductFinalPriceRequest {
    @NotNull
    private String clientId;

    @NotNull
    private String productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}

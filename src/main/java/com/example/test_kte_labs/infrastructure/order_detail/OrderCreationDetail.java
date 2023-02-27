package com.example.test_kte_labs.infrastructure.order_detail;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCreationDetail {
    @NotNull
    private String productId;
    @NotNull
    @Min(0)
    private Integer quantity;
}

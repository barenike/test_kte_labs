package com.example.test_kte_labs.infrastructure.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductCreationRequest {
    @NotNull
    @Size(max = 255)
    private String name;
    @NotNull
    private Double price;
    @NotNull
    @Size(max = 255)
    private String description;
}

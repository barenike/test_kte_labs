package com.example.test_kte_labs.infrastructure.client;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ChangeDiscountRatesRequest {
    @NotNull
    private String id;

    @NotNull
    @Range(min = 0, max = 18)
    private Integer firstDiscountRate;

    @NotNull
    @Range(min = 0, max = 18)
    private Integer secondDiscountRate;
}

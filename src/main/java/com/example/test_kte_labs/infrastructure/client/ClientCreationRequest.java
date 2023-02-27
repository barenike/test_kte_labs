package com.example.test_kte_labs.infrastructure.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ClientCreationRequest {
    @NotNull
    @Size(max = 255)
    private String name;
    @NotNull
    @Range(min = 0, max = 18)
    private Integer firstDiscountRate;
    @NotNull
    @Range(min = 0, max = 18)
    private Integer secondDiscountRate;
}

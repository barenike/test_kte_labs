package com.example.test_kte_labs.infrastructure.stats;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatsRequest {
    @NotNull
    private String clientId;
    @NotNull
    private String productId;
}

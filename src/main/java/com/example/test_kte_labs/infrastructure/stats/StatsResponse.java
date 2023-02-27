package com.example.test_kte_labs.infrastructure.stats;

import lombok.Data;

@Data
public class StatsResponse {
    private Integer chequeCount;

    private Double subtotalPriceSum;

    private Double discountSum;
}

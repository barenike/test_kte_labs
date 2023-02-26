package com.example.test_kte_labs.infrastructure.order;

import com.example.test_kte_labs.infrastructure.order_detail.OrderCreationDetail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreationRequest {
    @NotNull
    private String clientId;

    @NotNull
    private Double totalPrice;

    @NotNull
    @NotEmpty
    private List<OrderCreationDetail> orderCreationDetails;
}

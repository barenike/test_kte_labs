package com.example.test_kte_labs.controller.rest;

import com.example.test_kte_labs.infrastructure.order.OrderCreationRequest;
import com.example.test_kte_labs.model.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<String> createAndGetCheque(@RequestBody @Valid OrderCreationRequest orderCreationRequest) {
        String cheque = orderService.createAndGetCheque(orderCreationRequest);
        return new ResponseEntity<>(cheque, HttpStatus.CREATED);
    }
}

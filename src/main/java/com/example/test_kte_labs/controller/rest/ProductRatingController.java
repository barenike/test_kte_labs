package com.example.test_kte_labs.controller.rest;

import com.example.test_kte_labs.infrastructure.product.ProductExtraInfoResponse;
import com.example.test_kte_labs.infrastructure.product.ProductRateRequest;
import com.example.test_kte_labs.model.service.ProductRatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductRatingController {
    private final ProductRatingService productRatingService;

    public ProductRatingController(ProductRatingService productRatingService) {
        this.productRatingService = productRatingService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductExtraInfoResponse> getProductExtraInfo(@PathVariable(name = "id") String id) {
        final ProductExtraInfoResponse response = productRatingService.getProductExtraInfoResponse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/product/rate")
    public ResponseEntity<?> rateProduct(@RequestBody @Valid ProductRateRequest productRateRequest) {
        productRatingService.rateProduct(productRateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.example.test_kte_labs.controller.rest;

import com.example.test_kte_labs.infrastructure.product.ProductCreationRequest;
import com.example.test_kte_labs.infrastructure.product.ProductFinalPriceRequest;
import com.example.test_kte_labs.infrastructure.product.ProductResponse;
import com.example.test_kte_labs.model.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<?> create(@RequestBody @Valid ProductCreationRequest productCreationRequest) {
        productService.create(productCreationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean isDeleted = productService.delete(id);
        return isDeleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        final List<ProductResponse> products = productService.getProductResponseList();
        return products != null && !products.isEmpty()
                ? new ResponseEntity<>(products, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/product/final_price")
    public ResponseEntity<Double> getProductFinalPrice(@RequestBody @Valid ProductFinalPriceRequest productFinalPriceRequest) {
        Double productFinalPrice = productService.getProductFinalPrice(productFinalPriceRequest);
        return new ResponseEntity<>(productFinalPrice, HttpStatus.OK);
    }
}

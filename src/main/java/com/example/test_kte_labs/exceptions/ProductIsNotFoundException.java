package com.example.test_kte_labs.exceptions;

public class ProductIsNotFoundException extends RuntimeException {
    public ProductIsNotFoundException(String productId) {
        super(String.format("Product with %s UUID does not exist.", productId));
    }
}

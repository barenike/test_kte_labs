package com.example.test_kte_labs.exceptions;

public class TwoParametersInRequestException extends RuntimeException {
    public TwoParametersInRequestException(String clientId, String productId) {
        super(String.format(
                "You have passed clientId %s and productId %s. Please, choose only one one parameter to pass.",
                clientId,
                productId
        ));
    }
}

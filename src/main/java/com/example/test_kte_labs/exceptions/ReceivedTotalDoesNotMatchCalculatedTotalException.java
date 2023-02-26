package com.example.test_kte_labs.exceptions;

public class ReceivedTotalDoesNotMatchCalculatedTotalException extends RuntimeException {
    public ReceivedTotalDoesNotMatchCalculatedTotalException(Double calculatedTotalPrice, Double receivedTotalPrice) {
        super(String.format("Received total price %f does not match calculated total price %f",
                receivedTotalPrice,
                calculatedTotalPrice));
    }
}

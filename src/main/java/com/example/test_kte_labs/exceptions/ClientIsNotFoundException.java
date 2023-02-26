package com.example.test_kte_labs.exceptions;

public class ClientIsNotFoundException extends RuntimeException {
    public ClientIsNotFoundException(String clientId) {
        super(String.format("Client is not found by %s userId.", clientId));
    }
}

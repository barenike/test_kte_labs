package com.example.test_kte_labs.exceptions;

import java.util.UUID;

public class ClientHasNotRatedThisProductException extends RuntimeException {
    public ClientHasNotRatedThisProductException(UUID clientId, UUID productId) {
        super(String.format("Client %s has not rated product %s.", clientId, productId));
    }
}

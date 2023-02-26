package com.example.test_kte_labs.exceptions;

import java.util.UUID;

public class ClientHasAlreadyRatedThisProductException extends RuntimeException {
    public ClientHasAlreadyRatedThisProductException(UUID clientId, UUID productId) {
        super(String.format("Client %s has already rated product %s.", clientId, productId));
    }
}

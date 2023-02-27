package com.example.test_kte_labs.exceptions;

import java.util.UUID;

public class ClientDidNotBuyProductException extends RuntimeException {
    public ClientDidNotBuyProductException(UUID clientId, UUID productId) {
        super(String.format("Client %s did not buy the product %s. And that is why he cannot rate it.", clientId, productId));
    }
}

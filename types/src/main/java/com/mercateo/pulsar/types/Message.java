package com.mercateo.pulsar.types;

public record Message(int messageId, int userId, String message) {
}

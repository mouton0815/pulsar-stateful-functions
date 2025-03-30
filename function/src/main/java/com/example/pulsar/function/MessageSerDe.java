package com.example.pulsar.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercateo.pulsar.types.Message;
import org.apache.pulsar.functions.api.SerDe;

import java.io.IOException;

public class MessageSerDe implements SerDe<Message> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message deserialize(byte[] bytes) {
        try {
            return mapper.readValue(bytes, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] serialize(Message message) {
        try {
            return mapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

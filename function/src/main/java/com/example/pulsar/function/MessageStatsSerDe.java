package com.example.pulsar.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercateo.pulsar.types.MessageStats;
import org.apache.pulsar.functions.api.SerDe;

import java.io.IOException;

public class MessageStatsSerDe implements SerDe<MessageStats> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public MessageStats deserialize(byte[] bytes) {
        try {
            return mapper.readValue(bytes, MessageStats.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] serialize(MessageStats messageStats) {
        try {
            return mapper.writeValueAsBytes(messageStats);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

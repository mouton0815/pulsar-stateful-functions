package com.example.pulsar.function;

import com.mercateo.pulsar.types.Message;
import com.mercateo.pulsar.types.MessageStats;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

public class MessageFunction implements Function<Message, MessageStats> {
    private static final String TEXTS_KEY = "texts";
    private static final String WORDS_KEY = "words";

    @Override
    public MessageStats process(Message message, Context context) {
        context.getLogger().info("Process {}", message);
        try {
            context.incrCounter(TEXTS_KEY, 1L);
            final long texts = context.getCounter(TEXTS_KEY);
            context.incrCounter(WORDS_KEY, message.text().split(" ").length);
            final long words = context.getCounter(WORDS_KEY);
            final MessageStats stats = new MessageStats(message.userId(), texts, words);
            context.getLogger().info("Produce {}", stats);
            return stats;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

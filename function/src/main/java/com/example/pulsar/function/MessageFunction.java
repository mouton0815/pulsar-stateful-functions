package com.example.pulsar.function;

import com.mercateo.pulsar.types.Message;
import com.mercateo.pulsar.types.MessageStats;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

public class MessageFunction implements Function<Message, MessageStats> {

    @Override
    public MessageStats process(Message message, Context context) {
        context.getLogger().info("Process {}", message);
        try {
            final var textsKey = "texts_" + message.user();
            context.incrCounter(textsKey, 1L);
            var texts = context.getCounter(textsKey);
            final var wordsKey = "words_" + message.user();
            context.incrCounter(wordsKey, message.text().split(" ").length);
            final var words = context.getCounter(wordsKey);
            final var stats = new MessageStats(message.user(), texts, words);
            context.getLogger().info("Produce {}", stats);
            return stats;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

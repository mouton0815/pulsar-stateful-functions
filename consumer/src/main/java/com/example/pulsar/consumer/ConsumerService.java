package com.example.pulsar.consumer;

import com.mercateo.pulsar.types.Message;
import com.mercateo.pulsar.types.MessageStats;
import io.github.majusko.pulsar.PulsarMessage;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @PulsarConsumer(topic="messages", clazz= Message.class)
    public void consumeMessages(PulsarMessage<Message> msg) {
        log.info("-----> {}", msg.getValue());
    }

    @PulsarConsumer(topic="message-stats", clazz= MessageStats.class)
    public void consumeMessageStats(PulsarMessage<MessageStats> msg) {
        log.info("-----> {}", msg.getValue());
    }
}

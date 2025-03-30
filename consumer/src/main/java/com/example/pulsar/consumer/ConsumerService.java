package com.example.pulsar.consumer;

import com.mercateo.pulsar.types.Message;
import com.mercateo.pulsar.types.MessageStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @PulsarListener(topics="messages")
    public void consumeMessages(Message msg) {
        log.info("{}", msg);
    }

    @PulsarListener(topics="message-stats")
    public void consumeMessageStats(MessageStats stats) {
        log.info("{}", stats);
    }
}

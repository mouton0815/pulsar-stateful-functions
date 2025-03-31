package com.example.pulsar.producer;

import com.mercateo.pulsar.types.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class ScheduledMessageTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledMessageTask.class);

    private static final String[] USERS = {
            "Anne",
            "Fred",
            "John"
    };

    private static final String[] TEXTS = {
            "Lorem ipsum",
            "dolor sit amet",
            "consetetur sadipscing elitr,",
            "sed diam",
            "nonumy eirmod",
            "tempor",
            "invidunt",
            "ut labore et dolore"
    };

    private final PulsarTemplate<Message> producer;
    private int messageCount;

    @Autowired
    public ScheduledMessageTask(PulsarTemplate<Message> producer) {
        this.producer = producer;
        this.messageCount = 0;
    }

    @Scheduled(fixedRate = 3000, initialDelay = 2000)
    public void sendMessage() throws PulsarClientException {
        final var userId = ThreadLocalRandom.current().nextInt(0, USERS.length);
        final var textId = ThreadLocalRandom.current().nextInt(0, TEXTS.length);
        final var message = new Message(++messageCount, USERS[userId], TEXTS[textId]);
        producer.send("messages", message);
        log.info("Sent {}", message);
    }
}

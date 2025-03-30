package com.example.pulsar.producer;

import com.mercateo.pulsar.types.Message;
import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {
    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory().addProducer("messages", Message.class);
    }
}

package com.onwelo.practice.bts.fds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
public class TransferProducer {
    private final static String topicSend = "status-sender";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String status) {
        kafkaTemplate.send(topicSend, status);
    }
}

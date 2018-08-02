package com.onwelo.practice.bts.fds;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
public class TransferProducer {
    private final static String TOPICJSON = "make-transfer";
    private final static String TOPICSTATUS = "status-sender";

    private KafkaTemplate<String, String> kafkaTemplate;

    public TransferProducer(KafkaTemplate<String, String> stringStringKafkaTemplate) {
        this.kafkaTemplate = stringStringKafkaTemplate;
    }

    public void sendStatus(String status) {
        kafkaTemplate.send(TOPICSTATUS, status);
    }

    public void sendJson(String json) {
        kafkaTemplate.send(TOPICJSON, json);
    }
}

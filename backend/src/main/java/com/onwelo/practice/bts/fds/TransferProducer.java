package com.onwelo.practice.bts.fds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
public class TransferProducer {
    private final static String TOPICJSON = "make-transfer";
    private final static String TOPICSTATUS = "status-sender";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendStatus(String status) {
        kafkaTemplate.send(TOPICSTATUS, status);
    }

    public void sendJson(String json) {
        kafkaTemplate.send(TOPICJSON, json);
    }
}

package com.onwelo.practice.bts.fds;

import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
public class Saga {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferConsumer.class);
    private final static String topicStatus = "status-sender";
    private static Map<Long, ArrayList<String[]>> transfers = new HashMap<>();

    @KafkaListener(topics = topicStatus, groupId = "saga")
    public void receive(String status) {
        String[] transferStatus = status.split(",");
    }

    private void addToMap(String[] transferStatus) {
    }

    private void checkTransfers() {

    }
}

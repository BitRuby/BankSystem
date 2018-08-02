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
    private static Map<Long, Byte> transfers = new HashMap<>();

    @KafkaListener(topics = topicStatus, groupId = "saga")
    public void receive(String status) {
        String[] transferStatus = status.split(",");
    }

    private void addToMap(String[] transferStatus) {
        Long id = Long.valueOf(transferStatus[0]);
        byte checkSum = 0;

        if (transfers.containsKey(Long.valueOf(transferStatus[0]))) {
            checkSum = transfers.get(id);
        }

        if("APPROVED".equals(transferStatus[2])) {
            checkSum |= (1 << Byte.valueOf(transferStatus[1]));
        }

        transfers.put(Long.valueOf(transferStatus[0]), checkSum);
    }

    private void checkTransfers() {

    }
}

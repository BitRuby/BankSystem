package com.onwelo.practice.bts.kafka;

import com.onwelo.practice.bts.entity.Transfer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

public class TransferConsumer {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferConsumer.class);
    @Value("${kafka.topic.transfer}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Transfer> kafkaTemplate;

    @KafkaListener(topics = "make-transfer", groupId = "transfer1")
    public void receive(Transfer transfer) {
        Logger.debug("receive transfer='{}'", transfer.toString());
    }
//
//    @KafkaListener(topics = "make-transfer", groupId = "transfer")
//    public void receive2(Transfer transfer) {
//        Logger.debug("receive transfer='{}'", transfer.toString());
//    }
}

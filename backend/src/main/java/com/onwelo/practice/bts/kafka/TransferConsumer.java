package com.onwelo.practice.bts.kafka;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
public class TransferConsumer {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferConsumer.class);
    // @Value("${kafka.topic.transfer}")
    private String topic = "make-transfer";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "make-transfer", groupId = "transfer1")
    public void receive(String aLong) {
        Logger.debug("receive transfer='{}'", aLong);
    }

   @KafkaListener(topics = "make-transfer", groupId = "transfer")
    public void receive2(String aLong) {
        Logger.debug("receive transfer='{}'", aLong);
    }
}

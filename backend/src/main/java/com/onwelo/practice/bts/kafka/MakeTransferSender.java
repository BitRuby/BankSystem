package com.onwelo.practice.bts.kafka;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component

public class MakeTransferSender {

    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(MakeTransferListenner.class);

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public MakeTransferSender(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private String topic = "make-transfer";


    @Scheduled(cron = "0 * * * * *")
    public void someMainMethod() {
        Logger.info("** Outgoing kafka topic message **");
        send("hello -> " + LocalDate.now());
    }


    private void send(String message) {
        Logger.debug("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }
}
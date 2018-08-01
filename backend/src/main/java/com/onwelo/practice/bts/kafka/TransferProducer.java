package com.onwelo.practice.bts.kafka;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class TransferProducer {

    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferProducer.class);
    @Value("${kafka.topic.transfer}")
    private String topic;

    @Autowired
    private TransferService transferService;

    @Autowired
    private KafkaTemplate<String, Transfer> kafkaTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void send() {
        Transfer transfer = transferService.getTransferById(1L);
        Logger.debug("sending transfer='{}'", transfer.toString());
        kafkaTemplate.send(topic, transfer);
    }
}

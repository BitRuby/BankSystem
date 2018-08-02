package com.onwelo.practice.bts.fds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class TransferProducer {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferProducer.class);
    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${fds.topic.transfer}")
    private String topic = "make-transfer";

    @Autowired
    private TransferService transferService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void send() throws JsonProcessingException {
        Transfer transfer = transferService.getTransferById(1L);
        String jsonContent = mapper.writeValueAsString(transfer);
        Logger.debug("sending transfer='{}'", transfer.toString());
        kafkaTemplate.send(topic, jsonContent);
    }
}

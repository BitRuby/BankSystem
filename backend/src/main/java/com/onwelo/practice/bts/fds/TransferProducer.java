package com.onwelo.practice.bts.fds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class TransferProducer {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferProducer.class);
    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${kafka.topic.transfer}")
    private String sendTopic;

    @Autowired
    private TransferService transferService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(Transfer transfer) throws JsonProcessingException {
        String jsonContent = mapper.writeValueAsString(transfer);
        Logger.debug("sending transfer='{}'", transfer.toString());
        kafkaTemplate.send(sendTopic, jsonContent);
    }
}

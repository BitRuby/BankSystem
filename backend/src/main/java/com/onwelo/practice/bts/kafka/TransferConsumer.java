package com.onwelo.practice.bts.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.onwelo.practice.bts.entity.Transfer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;

@EnableKafka
public class TransferConsumer {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferConsumer.class);
    // @Value("${kafka.topic.transfer}")
    private String topic = "make-transfer";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "make-transfer", groupId = "transfer1")
    public void receive(String aLong) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Transfer.class, new TransferDeserializer());
        mapper.registerModule(module);

        Transfer transfer = mapper.readValue(aLong, Transfer.class);
        Logger.debug("receive transfer='{}'", transfer.toString());
    }

   @KafkaListener(topics = "make-transfer", groupId = "transfer")
    public void receive2(String aLong) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       SimpleModule module = new SimpleModule();
       module.addDeserializer(Transfer.class, new TransferDeserializer());
       mapper.registerModule(module);

       Transfer transfer = mapper.readValue(aLong, Transfer.class);
       Logger.debug("receive transfer2='{}'", transfer.toString());
    }
}

package com.onwelo.practice.bts.fds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.Currency;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.math.BigDecimal;

public class TransferValidatorEUR {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferConsumer.class);

    private String reciveTopic;

    private String sendTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public TransferValidatorEUR(@Value("${kafka.topic.transfer}") String reciveTopic, @Value("${kafka.topic.status}") String sendTopic) {
        this.reciveTopic = reciveTopic;
        this.sendTopic = sendTopic;
    }

    @KafkaListener(topics = "make-transfer", groupId = "transfer2")
    public void receive(String aLong) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Transfer.class, new TransferDeserializer());
        mapper.registerModule(module);
        Transfer transfer = mapper.readValue(aLong, Transfer.class);
        Logger.debug("TransferValidatorEUR - > receive - > transfer='{}'", transfer.toString());

        send(validateTransfer(transfer));
    }


    private String validateTransfer(Transfer transfer) {

        if (transfer.getCurrency().equals(Currency.EUR)) {
            if (transfer.getValue().compareTo(BigDecimal.valueOf(4000)) > 0) {
                return transfer.getId() + ",1," + TransferStatus.CANCELED.toString();
            } else {
                return transfer.getId() + ",1," + TransferStatus.APPROVED.toString();
            }
        } else {
            return transfer.getId() + ",1," + TransferStatus.APPROVED.toString();
        }
    }

    private void send(String status) {
        kafkaTemplate.send(sendTopic, status);
    }
}

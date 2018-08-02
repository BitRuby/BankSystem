package com.onwelo.practice.bts.fds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.Currency;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.math.BigDecimal;

@EnableKafka
public class TransferValidatorPLN {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferConsumer.class);
    private final static String topicReceive = "make-transfer";
    private final static String topicSend = "status-sender";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = topicReceive, groupId = "transfer1")
    public void receive(String jsonTransfer) throws IOException {
        Logger.info("Kafka: pln validator");
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Transfer.class, new TransferDeserializer());
        mapper.registerModule(module);

        Transfer transfer = mapper.readValue(jsonTransfer, Transfer.class);
        String status = validate(transfer);
        sendStatus(status);
    }

    private String validate(Transfer transfer) {
        if (transfer.getCurrency().equals(Currency.PLN)) {
            if (transfer.getValue().compareTo(BigDecimal.valueOf(15000L)) > 0) {
                return transfer.getId() + ",0," + TransferStatus.CANCELED.toString();
            } else {
                return transfer.getId() + ",0," + TransferStatus.APPROVED.toString();
            }
        } else {
            return transfer.getId() + ",0," + TransferStatus.APPROVED.toString();
        }
    }

    private void sendStatus(String status) {
        kafkaTemplate.send(topicSend, status);
    }
}

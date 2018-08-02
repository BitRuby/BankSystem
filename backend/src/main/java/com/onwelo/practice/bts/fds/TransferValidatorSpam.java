package com.onwelo.practice.bts.fds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.Currency;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@EnableKafka
@Configurable
public class TransferValidatorSpam {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferConsumer.class);
    private final static String topicReceive = "make-transfer";
    private final static String topicSend = "status-sender";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TransferService transferService;

    @KafkaListener(topics = topicReceive, groupId = "transfer3")
    public void receive(String jsonTransfer) throws IOException {
        Logger.info("Kafka: spam validator");
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Transfer.class, new TransferDeserializer());
        mapper.registerModule(module);

        Transfer transfer = mapper.readValue(jsonTransfer, Transfer.class);
        String status = validate(transfer);
        sendStatus(status);
    }

    private String validate(Transfer transfer) {
        Transfer last = transferService.getSecondTransferSortedByCreateTime(transfer.getAccountId().getId());
        if (last != null) {
            if (ChronoUnit.SECONDS.between(last.getCreateTime(), transfer.getCreateTime()) < 30L) {
                return transfer.getId() + ",3,CANCELED";
            } else {
                return transfer.getId() + ",3,APPROVED";
            }
        } else {
            return transfer.getId() + ",3,APPROVED";
        }
    }

    private void sendStatus(String status) {
        kafkaTemplate.send(topicSend, status);
    }
}

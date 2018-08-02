package com.onwelo.practice.bts.fds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

@EnableKafka
@Component
public class TransferValidatorSpam {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferValidatorSpam.class);
    private final static String topicReceive = "make-transfer";

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferProducer transferProducer;

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
                return transfer.getId() + ",2," + TransferStatus.CANCELED.toString();
            } else {
                return transfer.getId() + ",2," + TransferStatus.APPROVED.toString();
            }
        } else {
            return transfer.getId() + ",2," + TransferStatus.APPROVED.toString();
        }
    }

    private void sendStatus(String status) {
        transferProducer.sendStatus(status);
    }
}

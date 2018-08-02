package com.onwelo.practice.bts.fds;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.TransferStatus;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
public class Saga {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(Saga.class);
    private static Map<Long, ValidatorMessage> transfers = new HashMap<>();
    private final static String topicStatus = "status-sender";

    @Autowired
    private TransferService transferService;

    @Getter
    @Setter
    private class ValidatorMessage {
        private boolean[] author = new boolean[3];
        private boolean[] status = new boolean[3];
    }

    @KafkaListener(topics = topicStatus, groupId = "saga")
    public void receive(String status) {
        Logger.info("Saga: received message");
        String[] transferStatus = status.split(",");
        addToMap(transferStatus);
        checkTransfers();
    }

    private void addToMap(String[] transferStatus) {
        Long transferId = Long.valueOf(transferStatus[0]);

        if (transfers.containsKey(transferId)) {
            ValidatorMessage message = transfers.get(transferId);

            boolean[] author = message.getAuthor();
            author[Integer.valueOf(transferStatus[1])] = true;

            boolean[] status = message.getStatus();
            if (transferStatus[2].equals(TransferStatus.APPROVED.toString())) {
                status[Integer.valueOf(transferStatus[1])] = true;
            }

            transfers.replace(transferId, message);
        } else {
            ValidatorMessage message = new ValidatorMessage();
            boolean[] author = message.getAuthor();
            author[Integer.valueOf(transferStatus[1])] = true;

            boolean[] status = message.getStatus();
            if (transferStatus[2].equals(TransferStatus.APPROVED.toString())) {
                status[Integer.valueOf(transferStatus[1])] = true;
            }

            transfers.put(transferId, message);
        }
    }

    private void checkTransfers() {
        for (Map.Entry<Long, ValidatorMessage> entry : transfers.entrySet()) {
            ValidatorMessage message = entry.getValue();
            boolean[] author = message.getAuthor();
            if (author[0] && author[1] && author[2]) {
                boolean[] status = message.getStatus();
                if (status[0] && status[1] && status[2]) {
                    Transfer transfer = transferService.getTransferById(entry.getKey());
                    transfer.setStatus(TransferStatus.APPROVED);
                    transferService.updateTransfer(transfer);
                } else {
                    Transfer transfer = transferService.getTransferById(entry.getKey());
                    transfer.setStatus(TransferStatus.CANCELED);
                    transferService.updateTransfer(transfer);
                }
            }
        }
    }
}

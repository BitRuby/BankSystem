package com.onwelo.practice.bts.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.BankAccountService;
import com.onwelo.practice.bts.utils.Currency;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransferDeserializer extends StdDeserializer<Transfer> {

    @Autowired
    private BankAccountService bankAccountService;

    public TransferDeserializer() {
        this(null);
    }

    public TransferDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Transfer deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        Long id = Long.valueOf(node.get("id").asText());
        Long accId = Long.valueOf(node.get("accountId").asText());
        String title = node.get("title").asText();
        BigDecimal value = node.get("value").decimalValue();
        BankAccount accountId = new BankAccount();
        accountId.setId(accId);
        //BankAccount accountId = null;
        String accountNo = node.get("accountNo").asText();
        TransferStatus transferStatus = TransferStatus.valueOf(node.get("status").asText());
        TransferType transferType = TransferType.valueOf(node.get("transferType").asText());
        LocalDateTime createTime = LocalDateTime.parse(node.get("createTime").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime bookingDate = null;
        Currency currency = Currency.valueOf(node.get("currency").asText());
        Transfer transfer = new Transfer(title, value, accountId, accountNo, transferStatus, transferType, createTime, bookingDate, currency);
        transfer.setId(id);
        return transfer;
    }
}

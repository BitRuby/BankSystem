package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.session.SessionIncoming;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class CsvServiceTest {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(SessionIncoming.class);

    @Autowired
    private CsvService csvService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @BeforeEach
    void createTempTransfers() {
        BankAccount account1 = new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", BigDecimal.valueOf(500), BigDecimal.valueOf(0));
        BankAccount account2 = new BankAccount("74 1050 1416 1000 0092 0379 3907", "NieJan", "NieKowalski", BigDecimal.valueOf(500), BigDecimal.valueOf(0));
        bankAccountService.addBankAccount(account1);
        bankAccountService.addBankAccount(account2);

        Transfer transfer1 = new Transfer("Przelew numer jeden", BigDecimal.valueOf(100), account1, account2.getAccountNo(), TransferType.OUTGOING);
        transfer1.setStatus(TransferStatus.PENDING);
        Transfer transfer2 = new Transfer("Przelew numer dwa", BigDecimal.valueOf(200), account2, account1.getAccountNo(), TransferType.OUTGOING);
        transfer2.setStatus(TransferStatus.PENDING);
        transferService.addTransfer(transfer1);
        transferService.addTransfer(transfer2);
    }

    @AfterEach
    void cleanUp() throws Exception {
        bankAccountRepository.deleteAll();
        transferRepository.deleteAll();

        File file = new File("tmp.csv");
        if (!file.delete()) {
            throw new Exception("unable to delete file");
        }
    }

    @Test
    void createCsvFromDatabase() {
        ArrayList<Transfer> transfers = (ArrayList<Transfer>) transferService.getTransfersByStatus(TransferStatus.PENDING);
        StringWriter stringWriter = csvService.getCsvFromTransfers(transfers);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("tmp.csv"))) {
            bufferedWriter.write(stringWriter.toString());
        } catch (IOException e) {
            Logger.debug(e.getMessage(), e);
        }

        File file = new File("tmp.csv");
        Assertions.assertNotNull(file);
    }

    @Test
    void readTransfersFromCsv() {
        ArrayList<Transfer> tmpTransfers = (ArrayList<Transfer>) transferService.getTransfersByStatus(TransferStatus.PENDING);
        StringWriter stringWriter = csvService.getCsvFromTransfers(tmpTransfers);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("tmp.csv"))) {
            bufferedWriter.write(stringWriter.toString());
        } catch (IOException e) {
            Logger.debug(e.getMessage(), e);
        }

        File file = new File("tmp.csv");

        ArrayList<Transfer> transfers = csvService.getTransfersFromCsv(file);
        Assertions.assertNotNull(transfers);
    }
}

package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CsvServiceTest {

    @Autowired
    private CsvService csvService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @AfterEach
    void cleanUp() throws Exception {
        bankAccountRepository.deleteAll();
        transferRepository.deleteAll();

        File file = new File("tmp.csv");
        if (!file.delete())
            throw new Exception("unable to delete file");
    }

    @Test
    void createCsvFromDatabase() {
        createTempTransfers();
        File file = csvService.getCsvFile("tmp.csv");
        Assertions.assertNotNull(file);
    }

    @Test
    void readTransfersFromCsv() {
        createTempTransfers();
        ArrayList<Transfer> transfers = csvService.getTransfersFromCsv(csvService.getCsvFile("tmp.csv"));
        transfers.forEach(transfer -> System.out.println(transfer + "\n"));
        Assertions.assertNotNull(transfers);
    }

    private void createTempTransfers() {
        List<BankAccount> accounts  = new ArrayList<>();
        List<Transfer> transfers = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            accounts.add(new BankAccount(String.valueOf((123456 * i)), "Jan " + i, "Kowalski " + i, 100.0f * i, 0.0f));
        }

        for (BankAccount bankAccount : accounts) {
            bankAccountService.addBankAccount(bankAccount);
        }

        for (int i = 1; i < 10; i++) {
            transfers.add(new Transfer("przelew o tytule " + i, 100.0f * i, accounts.get(i - 1), accounts.get(i).getAccountNo(), TransferType.OUTGOING));
        }

        for (Transfer transfer : transfers)
            transfer.setStatus(TransferStatus.PENDING);

        transferRepository.saveAll(transfers);
    }
}

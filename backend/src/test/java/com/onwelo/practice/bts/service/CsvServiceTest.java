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
    private TransferService transferService;

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

        /*
        File file = new File("tmp.csv");
        if (!file.delete())
            throw new Exception("unable to delete file");
            */
    }

    @Test
    void createCsv() {
        BankAccount bankAccount1 = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        BankAccount bankAccount2 = new BankAccount("140159265125215510730339",
                "Jan", "Niekowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount1);
        bankAccountService.addBankAccount(bankAccount2);

        List<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            transfers.add(new Transfer("przelew", 100.0f, bankAccount1, bankAccount2.getAccountNo(), TransferType.INCOMING));

        for (Transfer transfer : transfers)
            transfer.setStatus(TransferStatus.PENDING);

        transferRepository.saveAll(transfers);

        File file = csvService.getCsvFile("tmp.csv");
        Assertions.assertNotNull(file);
    }
}

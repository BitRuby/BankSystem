package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BankAccountServiceTest implements Extension {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRepository transferRepository;

    @AfterEach
    public void cleanUp() {
        transferRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }

    @Test
    public void getAllBankAccounts() {
        List<BankAccount> bankAccounts = new ArrayList<BankAccount>() {{
            add(new BankAccount("140159260076545510730339",
                    "Jan", "Kowalski", 1000.0f, 0.0f));
            add(new BankAccount("330006100519786457841326",
                    "Jan", "Kowalski", 1000.0f, 0.0f));
        }};
        bankAccounts.forEach(bankAccountService::addBankAccount);
        assertNotNull(bankAccountService.getAllBankAccounts());
    }

    @Test
    public void getTransfers() {
        BankAccount bankIn = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        BankAccount bankOut = new BankAccount("330006100519786457841326",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        bankAccountService.addBankAccount(bankIn);
        bankAccountService.addBankAccount(bankOut);

        Transfer transfer1 = new Transfer("przelew", 100.0f, bankIn, bankOut.getAccountNo(), TransferType.INCOMING);
        Transfer transfer2 = new Transfer("przelew", 100.0f, bankOut, bankIn.getAccountNo(), TransferType.OUTGOING);
        transferService.addTransfer(transfer1);
        transferService.addTransfer(transfer2);

        assertNotNull(bankAccountService.getTransfers(bankIn.getId()));
        assertNotNull(bankAccountService.getTransfers(bankOut.getId()));

        transferService.deleteTransfer(transfer1.getId());
        transferService.deleteTransfer(transfer2.getId());

        assertNull(transferService.getTransferById(transfer1.getId()));
        assertNull(transferService.getTransferById(transfer2.getId()));
    }

    @Test
    public void getBankAccountById() {
        BankAccount bankAccount = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        Assertions.assertNotNull(bankAccountService.getBankAccountById(bankAccount.getId()));
    }

}
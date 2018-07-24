package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.exceptions.NotValidField;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class BankAccountServiceTest implements Extension {

    static final BigDecimal bd1000 = new BigDecimal(1000);
    static final BigDecimal bd100 = new BigDecimal(100);
    static final BigDecimal bd0 = new BigDecimal(0);

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
        List<BankAccount> bankAccounts = new ArrayList<>() {{
            add(new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", bd1000, bd0));
            add(new BankAccount("74 1050 1416 1000 0092 0379 3907", "Jan", "Kowalski", bd1000, bd0));
        }};
        bankAccounts.forEach(bankAccountService::addBankAccount);
        assertNotNull(bankAccountService.getAllBankAccounts());
    }

    @Test
    public void getTransfers() {
        BankAccount bankIn = new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", bd1000, bd0);
        BankAccount bankOut = new BankAccount("74 1050 1416 1000 0092 0379 3907", "Jan", "Kowalski", bd1000, bd0);
        bankAccountService.addBankAccount(bankIn);
        bankAccountService.addBankAccount(bankOut);

        Transfer transfer1 = new Transfer("przelew", bd100, bankIn, bankOut.getAccountNo(), TransferType.INCOMING);
        Transfer transfer2 = new Transfer("przelew", bd100, bankOut, bankIn.getAccountNo(), TransferType.OUTGOING);
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
        BankAccount bankAccount = new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", bd1000, bd0);

        bankAccountService.addBankAccount(bankAccount);
        Assertions.assertDoesNotThrow(() -> bankAccountService.getBankAccountById(bankAccount.getId()));
    }

    @Test
    void isValid() {
        assertTrue(bankAccountService.isValid("29 1160 2202 0000 0003 1193 5598"));
        assertTrue(bankAccountService.isValid("74 1050 1416 1000 0092 0379 3907"));
        assertFalse(bankAccountService.isValid("29 1160 2202 0000 0003 1193 5596"));
        assertFalse(bankAccountService.isValid("29 1160 2202 0000 0003 1193"));
    }
}
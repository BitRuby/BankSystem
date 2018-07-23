package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Bank;
import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.BankRepository;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.onwelo.practice.bts.service.BankAccountServiceTest.bd0;
import static com.onwelo.practice.bts.service.BankAccountServiceTest.bd100;
import static com.onwelo.practice.bts.service.BankAccountServiceTest.bd1000;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class TransferServiceTest implements Extension {

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private BankRepository bankRepository;

    @AfterEach
    public void cleanUp() {
        transferRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }

    @Test
    public void getAllTransfers() {
        BankAccount bankIn = new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", bd1000, bd0);
        List<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            transfers.add(new Transfer("przelew", bd100, bankIn, "74 1050 1416 1000 0092 0379 3907", TransferType.INCOMING));

        bankAccountService.addBankAccount(bankIn);
        transfers.forEach(transferService::addTransfer);
        assertNotNull(transferService.getAllTransfers());

        transferService.getAllTransfers().forEach(System.out::println);
    }

    @Test
    public void getTransfersByStatus() {
        BankAccount bankIn = new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", bd1000, bd0);
        List<Transfer> transfers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Transfer transfer = new Transfer("przelew", bd100, bankIn, "74 1050 1416 1000 0092 0379 3907", TransferType.INCOMING);
            transfer.setStatus(TransferStatus.REALIZED);
            transfers.add(transfer);
        }

        for (int i = 0; i < 10; i++) {
            Transfer transfer = new Transfer("przelew", bd100, bankIn, "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);
            transfer.setStatus(TransferStatus.PENDING);
            transfers.add(transfer);
        }

        bankAccountService.addBankAccount(bankIn);
        transfers.forEach(transferService::addTransfer);

        assertEquals(5, transferService.getTransfersByStatus(TransferStatus.REALIZED).size());
        assertEquals(10, transferService.getTransfersByStatus(TransferStatus.PENDING).size());
    }

    @Test
    public void getBank() {
        List<Bank> banks = new ArrayList<>() {{
            add(new Bank("Bank Millennium SA", "'Centrala'", "02-593", "'Warszawa'", "ul. Stanisława Żaryna 2A", "11600006"));
            add(new Bank("Bank Millennium SA", "Millennium - Centrum Rozliczeniowe", "80-887", "Gdańsk", "ul. Wały Jagiellońskie 10/16", "11602202"));
            add(new Bank("ING Bank Śląski SA", "Centrala - Centrum Płatności i Obsługi Klientów", "40-086", "Katowice", "ul. Sokolska 34 ", "10500002"));
            add(new Bank("ING Bank Śląski SA", "Centrala - Departament Księgowości Własnej", "40-086", "Katowice", "ul. Sokolska 34 ", "10500015"));
        }};
        bankService.addAll(banks);

        String Millennium = "29 1160 2202 0000 0003 1193 5598";
        String ING = "74 1050 1416 1000 0092 0379 3907";

        assertNotNull(transferService.getBank(Millennium));
        assertNotNull(transferService.getBank(ING));
        assertEquals("Bank Millennium SA", transferService.getBank(Millennium).getName());
        assertEquals("ING Bank Śląski SA", transferService.getBank(ING).getName());
        assertNull(transferService.getBank(""));
        assertNull(transferService.getBank("74 1050 1416 1000 0092 0379"));

        bankRepository.deleteAll();
    }
}
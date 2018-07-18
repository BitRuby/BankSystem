package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.TransferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
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

    @Test
    public void getAllTransfers() {
        BankAccount bankIn = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        List<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            transfers.add(new Transfer("przelew", 100.0f, bankIn, "240159260076545510730339", "outer"));

        bankAccountService.addBankAccount(bankIn);
        transfers.forEach(transferService::addTransfer);
        assertNotNull(transferService.getAllTransfers());

        transferService.getAllTransfers().forEach(System.out::println);

        transfers.stream().map(Transfer::getId).forEach(transferService::deleteTransfer);
    }

    @Test
    public void getTransfersByStatus() {
        BankAccount bankIn = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        List<Transfer> transfers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Transfer transfer = new Transfer("przelew", 100.0f, bankIn, "240159260076545510730339", "outer");
            transfer.setStatus("realized");
            transfers.add(transfer);
        }

        for (int i = 0; i < 10; i++) {
            Transfer transfer = new Transfer("przelew", 100.0f, bankIn, "240159260076545510730339", "outer");
            transfer.setStatus("pending");
            transfers.add(transfer);
        }

        bankAccountService.addBankAccount(bankIn);
        transfers.forEach(transferService::addTransfer);

        assertEquals(5, transferService.getTransfersByStatus("realized").size());
        assertEquals(10, transferService.getTransfersByStatus("pending").size());

        System.out.println("realized:");
        transferService.getTransfersByStatus("realized").forEach(System.out::println);
        System.out.println("pending:");
        transferService.getTransfersByStatus("pending").forEach(System.out::println);
    }

    @Test
    public void getTransferById() {
    }

    @Test
    public void addTransfer() {
    }

    @Test
    public void updateTransfer() {
    }

    @Test
    public void deleteTransfer() {
        BankAccount bankAccount = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        Transfer transfer = new Transfer("przelew", 100.0f, bankAccount, "240159260076545510730339", "inner");

        bankAccountService.addBankAccount(bankAccount);
        transferService.addTransfer(transfer);

        assertNotNull(bankAccountService.getBankAccountById(bankAccount.getId()));
        assertNotNull(transferService.getTransferById(transfer.getId()));

        transferService.deleteTransfer(transfer.getId());
        assertNull(transferService.getTransferById(transfer.getId()));
    }

    @AfterEach
    public void cleanUp() {
        transferRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }
}
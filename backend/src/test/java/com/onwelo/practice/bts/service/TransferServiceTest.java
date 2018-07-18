package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Test
    public void getAllTransfers() {
        BankAccount bankIn = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        List<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            transfers.add(new Transfer("przelew", 100.0f, bankIn, "240159260076545510730339", "outer"));

        bankAccountService.addBankAccount(bankIn);
        transfers.forEach(transferService::addTransfer);
        Assert.assertNotNull(transferService.getAllTransfers());

        transfers.stream().map(Transfer::getId).forEach(transferService::deleteTransfer);
        bankAccountService.deleteBankAccount(bankIn.getId());
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

        Assert.assertEquals(5, transferService.getTransfersByStatus("realized").size());
        Assert.assertEquals(10, transferService.getTransfersByStatus("pending").size());
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
    }
}
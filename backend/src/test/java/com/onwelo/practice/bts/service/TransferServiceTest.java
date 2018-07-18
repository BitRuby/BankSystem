package com.onwelo.practice.bts.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Test
    public void getAllTransfers() {
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
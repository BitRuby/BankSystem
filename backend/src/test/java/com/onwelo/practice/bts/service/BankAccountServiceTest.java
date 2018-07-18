package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
public class BankAccountServiceTest {
    @Autowired
    private BankAccountService bankAccountService;

    @Test
    public void testAddBankAccount() {
        BankAccount bankAccount = new BankAccount("952490266379177483889959776740",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        Assert.notNull(bankAccountService.getBankAccountById(bankAccount.getId()));
    }

}
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
public class BankAccountServiceTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransferService transferService;

    @Test
    public void getAllBankAccounts() {
        List<BankAccount> bankAccounts = new ArrayList<BankAccount>() {{
            add(new BankAccount("140159260076545510730339",
                    "Jan", "Kowalski", 1000.0f, 0.0f));
            add(new BankAccount("330006100519786457841326",
                    "Jan", "Kowalski", 1000.0f, 0.0f));
        }};
        bankAccounts.forEach(bankAccountService::addBankAccount);
        Assert.assertNotNull(bankAccountService.getAllBankAccounts());
        bankAccounts.stream().map(BankAccount::getId).forEach(bankAccountService::deleteBankAccount);
    }

    @Test
    public void getTransfers() {
        BankAccount bankIn = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        BankAccount bankOut = new BankAccount("330006100519786457841326",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        bankAccountService.addBankAccount(bankIn);
        bankAccountService.addBankAccount(bankOut);

        Transfer transfer1 = new Transfer("przelew", 100.0f, bankIn, bankOut.getAccountNo(), "inner");
        Transfer transfer2 = new Transfer("przelew", 100.0f, bankOut, bankIn.getAccountNo(), "inner");
        transferService.addTransfer(transfer1);
        transferService.addTransfer(transfer2);

        transferService.deleteTransfer(transfer1.getId());
        transferService.deleteTransfer(transfer2.getId());
        bankAccountService.deleteBankAccount(bankIn.getId());
        bankAccountService.deleteBankAccount(bankOut.getId());
    }

    @Test
    public void getBankAccountById() {
        BankAccount bankAccount = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        BankAccount bankAccount2 = bankAccountService.getBankAccountById(bankAccount.getId());
        Assert.assertEquals(bankAccount.getId(), bankAccount2.getId());

        bankAccountService.deleteBankAccount(bankAccount.getId());
    }

    @Test
    public void getBankAccountByNumber() {
        BankAccount bankAccount = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        Assert.assertEquals(bankAccount.getId(),
                bankAccountService.getBankAccountByNumber(bankAccount.getAccountNo()).getId());

        bankAccountService.deleteBankAccount(bankAccount.getId());
    }

    @Test
    public void addBankAccount() {
        BankAccount bankAccount = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        Assert.assertNotNull(bankAccountService.getBankAccountById(bankAccount.getId()));

        bankAccountService.deleteBankAccount(bankAccount.getId());
    }

    @Test
    public void updateBankAccount() {
        BankAccount bankAccount = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        bankAccount.setFirstName("Adam");
        bankAccountService.updateBankAccount(bankAccount);
        Assert.assertEquals(bankAccount.getFirstName(), bankAccountService.getBankAccountById(bankAccount.getId()).getFirstName());

        bankAccountService.deleteBankAccount(bankAccount.getId());
    }

//    @Test
//    public void deleteBankAccount() {
//        BankAccount bankAccount = new BankAccount("140159260076545510730339",
//                "Jan", "Kowalski", 1000.0f, 0.0f);
//
//        bankAccountService.addBankAccount(bankAccount);
//        bankAccountService.deleteBankAccount(bankAccount.getId());
//        Assert.assertNull(bankAccountService.getBankAccountById(bankAccount.getId()));
//    }

    @Test
    public void deactivateBankAccount() {
        BankAccount bankAccount = new BankAccount("240159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        bankAccountService.deactivateBankAccount(bankAccount.getId());
        Assert.assertNull(bankAccountService.getBankAccountById(bankAccount.getId()));
    }
}
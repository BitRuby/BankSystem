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
    public void getIncomingTransfers() {
        BankAccount bankIn = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        BankAccount bankOut = new BankAccount("330006100519786457841326",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        bankAccountService.addBankAccount(bankIn);
        bankAccountService.addBankAccount(bankOut);

        Transfer transfer = new Transfer("przelew", 100.0f, bankIn, bankOut);
        transferService.addTransfer(transfer);

        Assert.assertNotNull(transferService.getTransferById(transfer.getId()));
        ArrayList<Transfer> in = new ArrayList<>(
                bankAccountService.getBankAccountById(bankOut.getId()).getIncomingTransfers());
        Assert.assertNotNull(in);
        Assert.assertEquals(in.get(0).getId(), transferService.getTransferById(transfer.getId()).getId());

        transferService.deleteTransfer(transfer.getId());
        bankAccountService.deleteBankAccount(bankIn.getId());
        bankAccountService.deleteBankAccount(bankOut.getId());
    }

    @Test
    public void getOutgoingTransfers() {
        BankAccount bankIn = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        BankAccount bankOut = new BankAccount("330006100519786457841326",
                "Jan", "Kowalski", 1000.0f, 0.0f);
        bankAccountService.addBankAccount(bankIn);
        bankAccountService.addBankAccount(bankOut);

        Transfer transfer = new Transfer("przelew", 100.0f, bankIn, bankOut);
        transferService.addTransfer(transfer);

        Assert.assertNotNull(transferService.getTransferById(transfer.getId()));
        ArrayList<Transfer> out = new ArrayList<>(
                bankAccountService.getBankAccountById(bankIn.getId()).getOutgoingTransfers());
        Assert.assertNotNull(out);
        Assert.assertEquals(out.get(0).getId(), transferService.getTransferById(transfer.getId()).getId());

        transferService.deleteTransfer(transfer.getId());
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
                bankAccountService.getBankAccountByNumber(bankAccount.getBankNumber()).getId());

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

    @Test(expected = RuntimeException.class)
    public void deleteBankAccount() {
        BankAccount bankAccount = new BankAccount("140159260076545510730339",
                "Jan", "Kowalski", 1000.0f, 0.0f);

        bankAccountService.addBankAccount(bankAccount);
        bankAccountService.deleteBankAccount(bankAccount.getId());
        Assert.assertNull(bankAccountService.getBankAccountById(bankAccount.getId()));
    }
}
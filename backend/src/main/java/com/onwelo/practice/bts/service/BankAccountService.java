package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public List<BankAccount> getAllBankAccounts() {
        return new ArrayList<>(bankAccountRepository.findAll());
    }

    public List<Transfer> getTransfers(Long id) {
        BankAccount bankAccount = getBankAccountById(id);

        if (bankAccount != null) {
            return new ArrayList<>(bankAccount.getTransfers());
        } else {
            return null;
        }
    }

    public BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    public BankAccount getBankAccountByNumber(String accountNo) {
        return bankAccountRepository.findByAccountNumber(accountNo).orElse(null);
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public void deactivateBankAccount(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);

        if (bankAccount != null) {
            bankAccount.setActive(false);
            bankAccountRepository.save(bankAccount);
        }
    }

    public void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
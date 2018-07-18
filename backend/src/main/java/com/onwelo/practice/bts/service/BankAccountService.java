package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public List<BankAccount> getAllBankAccounts() {
        return new ArrayList<>(bankAccountRepository.findAll());
    }

    public Collection getIncomingTransfers(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount != null)
            return bankAccount.getIncomingTransfers();
        else return null;
    }

    public Collection getOutgoingTransfers(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount != null)
            return bankAccount.getOutgoingTransfers();
        else return null;
    }

    public BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    public BankAccount getBankAccountByNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
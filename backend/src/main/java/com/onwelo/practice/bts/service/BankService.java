package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Bank;
import com.onwelo.practice.bts.exceptions.NotValidField;
import com.onwelo.practice.bts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    public List<Bank> addAll(Iterable<Bank> bank) {
        return bankRepository.saveAll(bank);
    }

    public Bank getBank(String accountNo) {
        if (accountNo == null || ((accountNo = accountNo.replace(" ", "")).length() != 26)
                || !BankAccountService.isValid(accountNo)) {
            throw new NotValidField(accountNo + " IBAN is incorrect");
        }

        return bankRepository.findFirstBySortCodeIsLike(accountNo.substring(2, 10) + "%").orElse(null);
    }
}

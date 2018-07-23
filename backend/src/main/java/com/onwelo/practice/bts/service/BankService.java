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

    /**
     * @see <a href="https://pl.wikipedia.org/wiki/Mi%C4%99dzynarodowy_numer_rachunku_bankowego#Sprawdzanie_i_wyliczanie_cyfr_kontrolnych">Wikipedia - polish</a>
     * @see <a href="https://en.wikipedia.org/wiki/International_Bank_Account_Number#Validating_the_IBAN">Wikipedia - english</a>
     */
    public Bank getBank(String accountNo) {
        if (accountNo == null || ((accountNo = accountNo.replace(" ", "")).length() != 26)
                || !BankAccountService.isValid(accountNo)) {
            throw new NotValidField(accountNo + " IBAN is incorrect");
        }

        return bankRepository.findFirstBySortCodeIsLike(accountNo.substring(2, 10) + "%").orElse(null);
    }
}

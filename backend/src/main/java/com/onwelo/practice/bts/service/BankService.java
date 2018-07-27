package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Bank;
import com.onwelo.practice.bts.exceptions.NotValidField;
import com.onwelo.practice.bts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
                || !isValid(accountNo)) {
            throw new NotValidField(accountNo + " IBAN is incorrect");
        }

        return bankRepository.findFirstBySortCodeIsLike(accountNo.substring(2, 10) + "%").orElse(null);
    }

    /**
     * @see <a href="https://pl.wikipedia.org/wiki/Mi%C4%99dzynarodowy_numer_rachunku_bankowego#Sprawdzanie_i_wyliczanie_cyfr_kontrolnych">Wikipedia - polish</a>
     * @see <a href="https://en.wikipedia.org/wiki/International_Bank_Account_Number#Validating_the_IBAN">Wikipedia - english</a>
     */
    public static boolean isValid(String accountNo) {
        if (accountNo == null) return false;

        //2521 otrzymujemy po rozkodowaniu PL - rozpatrujemy tylko polskie rachunki
        accountNo = accountNo.replace(" ", "");

        if (accountNo.length() != 26) return false;

        accountNo = accountNo.substring(2, 26) + "2521" + accountNo.substring(0, 2);

        BigInteger value = new BigInteger(accountNo);
        return value.mod(new BigInteger("97")).intValue() == 1;
    }

    public static String getBankID(String accountNo) {
        if (accountNo == null || accountNo.length() != 26) {
            return null;
        }

        return accountNo.substring(2, 6);
    }
}

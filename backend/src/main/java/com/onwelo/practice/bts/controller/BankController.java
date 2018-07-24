package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.Bank;
import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/banks", produces = "application/json")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping("/find-by-account-no/{accountNo}")
    public String getBankName(@PathVariable("accountNo") String accountNo) {
        Bank bank = bankService.getBank(accountNo);
        return bank.getName() + " - " + bank.getDepartment();
    }
}

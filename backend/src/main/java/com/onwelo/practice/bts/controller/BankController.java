package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.Bank;
import com.onwelo.practice.bts.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping("/find-by-account-no/{accountNo}")
    public Bank getBankName(@PathVariable("accountNo") String accountNo) {
        return bankService.getBank(accountNo);
    }
}

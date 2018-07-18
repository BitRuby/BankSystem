package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
@RequestMapping("/api")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @RequestMapping(method = RequestMethod.GET, path = "/accounts")
    public ResponseEntity getBankAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }
}

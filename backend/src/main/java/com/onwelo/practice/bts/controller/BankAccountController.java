package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
@RequestMapping("/api")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @RequestMapping(method = RequestMethod.GET, path = "/accounts")
    public ResponseEntity index() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/accounts/:id")
    public ResponseEntity show(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bankAccountService.getBankAccountById(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/accounts")
    public ResponseEntity.BodyBuilder create(@RequestBody BankAccount bankAccount) {
        bankAccountService.addBankAccount(bankAccount);
        return ResponseEntity.ok();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/accounts/:id")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody BankAccount bankAccount) {
        BankAccount bankAcc = bankAccountService.getBankAccountById(id);

        if(bankAcc == null) {
            return ResponseEntity.notFound().build();
        } else {

            return ResponseEntity.ok(bankAccount);
        }
    }
}

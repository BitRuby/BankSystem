package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/accounts")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    @GetMapping(path = "/:id")
    public ResponseEntity show(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bankAccountService.getBankAccountById(id));
    }

    @PostMapping
    public ResponseEntity.BodyBuilder create(@RequestBody BankAccount bankAccount) {
        bankAccountService.addBankAccount(bankAccount);
        return ResponseEntity.ok();
    }

    @PutMapping(path = "/:id")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody BankAccount bankAccount) {
        BankAccount bankAcc = bankAccountService.getBankAccountById(id);

        if(bankAcc == null) {
            return ResponseEntity.notFound().build();
        } else {

            return ResponseEntity.ok(bankAccount);
        }
    }
}

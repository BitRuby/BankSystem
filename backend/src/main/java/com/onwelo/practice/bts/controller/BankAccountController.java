package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    @GetMapping(path = "/{id}")
    public BankAccount show(@PathVariable("id") Long id) {
        return bankAccountService.getBankAccountById(id);
    }

    @PostMapping
    public BankAccount create(@Valid @RequestBody BankAccount bankAccount) {
        return bankAccountService.addBankAccount(bankAccount);
    }

    @PutMapping(path = "/{id}")
    public BankAccount update(@PathVariable("id") Long id, @Valid @RequestBody BankAccount bankAccount) {
        bankAccount.setId(id);
        return bankAccountService.updateBankAccount(bankAccount);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bankAccountService.deactivateBankAccount(id));
    }
}

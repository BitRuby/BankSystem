package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity create(@RequestBody BankAccount bankAccount) {
        bankAccountService.addBankAccount(bankAccount);
        return ResponseEntity.ok(bankAccount);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody BankAccount bankAccount) {
        bankAccount.setId(id);
        bankAccountService.updateBankAccount(bankAccount);
        return ResponseEntity.ok(bankAccount);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        bankAccountService.deactivateBankAccount(id);
        return ResponseEntity.ok().build();
    }
}

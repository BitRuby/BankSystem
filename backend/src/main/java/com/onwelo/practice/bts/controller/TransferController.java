package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transfers", produces = "application/json")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok(transferService.getAllTransfers());
    }

    @GetMapping(path = "/{id}")
    public Transfer show(@PathVariable("id") Long id) {
        return transferService.getTransferById(id);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity transferByUser(@PathVariable("id") Long id, Pageable pageable) {
        return ResponseEntity.ok(transferService.getTransferByAccountId(id, pageable));
    }

    @PostMapping
    public Transfer create(@RequestBody Transfer transfer) {
        return transferService.addTransfer(transfer);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(transferService.deactivateTransfer(id));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}

package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transfers")
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

    @PostMapping
    public Transfer create(@Valid @RequestBody Transfer transfer) {
        return transferService.addTransfer(transfer);
    }

    @PutMapping(path = "/{id}")
    public Transfer update(@PathVariable("id") Long id, @Valid @RequestBody Transfer transfer) {
        transfer.setId(id);
        return transferService.updateTransfer(transfer);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(transferService.deactivateTransfer(id));
    }
}

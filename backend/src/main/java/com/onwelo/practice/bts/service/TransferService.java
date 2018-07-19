package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    public List<Transfer> getAllTransfers() {
        return new ArrayList<>(transferRepository.findAll());
    }

    public List<Transfer> getTransfersByStatus(TransferStatus status) {
        return transferRepository.findAllByStatus(status);
    }

    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id).orElse(null);
    }

    public void addTransfer(Transfer transfer) {
        transferRepository.save(transfer);
    }

    public void updateTransfer(Transfer transfer) {
        transferRepository.save(transfer);
    }

    public void deleteTransfer(Long id) {
        transferRepository.deleteById(id);
    }
}
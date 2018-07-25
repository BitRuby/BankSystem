package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.exceptions.MissingFieldException;
import com.onwelo.practice.bts.exceptions.NotFoundException;
import com.onwelo.practice.bts.exceptions.NotValidField;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return transferRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("could not found transfer with id=" + id));
    }

    public Transfer addTransfer(Transfer transfer) {
        if (transfer.getAccountNo() == null) {
            throw new MissingFieldException("missing transfer field= account no");
        } else if (!BankService.isValid(transfer.getAccountNo())) {
            throw new NotValidField(transfer.getAccountNo() + " IBAN is incorrect");
        }

        transfer.setId(null);

        return transferRepository.save(transfer);
    }

    public Transfer updateTransfer(Transfer transfer) {
        if (transfer.getAccountNo() == null) {
            throw new MissingFieldException("missing transfer field= account no");
        } else if (!BankService.isValid(transfer.getAccountNo())) {
            throw new NotValidField(transfer.getAccountNo() + " IBAN is incorrect");
        }

        return transferRepository.save(transfer);
    }

    public Transfer deactivateTransfer(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("could not found transfer with id=" + id));

        transfer.setActive(false);
        return transferRepository.save(transfer);
    }

    public Page<Transfer> getTransferByAccountId(Long id, Pageable pageable) {
        return transferRepository.findAllByAccountId_IdOrderByCreateTimeDesc(id, pageable);
    }
}
package com.onwelo.practice.bts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.exceptions.ForbiddenException;
import com.onwelo.practice.bts.exceptions.MissingFieldException;
import com.onwelo.practice.bts.exceptions.NotFoundException;
import com.onwelo.practice.bts.exceptions.NotValidField;
import com.onwelo.practice.bts.fds.TransferProducer;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.session.SessionOutgoing;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {

    private final String bankIBAN;
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private BankAccountService bankAccountService;

    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(TransferService.class);
    @Autowired
    private TransferProducer transferProducer;

    @Autowired
    public TransferService(@Value("${bank.iban}") String bankIBAN) {
        this.bankIBAN = bankIBAN;
    }

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
        BankAccount bankAccount = bankAccountService.getBankAccountById(transfer.getAccountId().getId());
        LocalDateTime now = LocalDateTime.now();

        if (transfer.getAccountNo() == null) {
            throw new MissingFieldException("missing transfer field= account no");
        } else if (!BankService.isValid(transfer.getAccountNo())) {
            throw new NotValidField(transfer.getAccountNo() + " IBAN is incorrect");
        }

        if (bankAccount.getMoneyAmount().compareTo(transfer.getValue()) < 0) {
            throw new ForbiddenException("not enough money to finalize transfer");
        }

        transfer.setBookingDate(now);

        if (BankService.getBankID(transfer.getAccountNo()).equals(bankIBAN)) {
            innerTransfer(bankAccount.getAccountNo(), transfer);
        }

        switch (transfer.getTransferType()) {
            case OUTGOING:
                transfer.setCreateTime(now);

                if (bankAccount.getMoneyAmount().compareTo(transfer.getValue()) > 0) {
                    bankAccount.setMoneyAmount(bankAccount.getMoneyAmount().subtract(transfer.getValue()));
                    bankAccount.setMoneyBlocked(bankAccount.getMoneyBlocked().add(transfer.getValue()));
                } else {
                    Logger.debug("New transfer: not enough money to add new transfer");
                }

                break;

            case INCOMING:
                bankAccount.setMoneyAmount(bankAccount.getMoneyAmount().add(transfer.getValue()));
                break;

            default:
                transfer.setTransferType(TransferType.OUTGOING);
                transfer.setCreateTime(now);

                if (bankAccount.getMoneyAmount().compareTo(transfer.getValue()) > 0) {
                    bankAccount.setMoneyAmount(bankAccount.getMoneyAmount().subtract(transfer.getValue()));
                    bankAccount.setMoneyBlocked(bankAccount.getMoneyBlocked().add(transfer.getValue()));
                } else {
                    Logger.debug("New transfer: not enough money to add new transfer");
                }

                break;
        }

        bankAccountService.updateBankAccount(bankAccount);

        transfer = transferRepository.save(transfer);
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = null;
        try {
            jsonContent = mapper.writeValueAsString(transfer);
        } catch (JsonProcessingException e) {
            Logger.debug(e.getMessage(), e);
        }

        transferProducer.sendJson(jsonContent);

        return transfer;
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
        return transferRepository.findAllByAccountId_Id(id, pageable);
    }

    private Transfer innerTransfer(String senderAccNo, Transfer transfer) {
        LocalDateTime now = LocalDateTime.now();

        Transfer incoming = new Transfer(transfer.getTitle(),
                transfer.getValue(),
                bankAccountService.getBankAccountByNumber(transfer.getAccountNo()),
                senderAccNo,
                TransferStatus.REALIZED,
                TransferType.INCOMING,
                now, now,
                transfer.getCurrency());

        transfer.setStatus(TransferStatus.REALIZED);

        incoming.getAccountId().setMoneyAmount(incoming.getAccountId().getMoneyAmount().add(transfer.getValue()));
        bankAccountService.updateBankAccount(incoming.getAccountId());

        return transferRepository.save(incoming);
    }

    public Transfer getSecondTransferSortedByCreateTime(Long accountId) {
        ArrayList<Transfer> ar = transferRepository.findTop2ByAccountId_IdOrderByCreateTimeDesc(accountId);

        if (ar != null && !ar.isEmpty()) {
            return ar.get(1);
        } else {
            return null;
        }
    }
}
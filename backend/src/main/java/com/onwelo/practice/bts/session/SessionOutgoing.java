package com.onwelo.practice.bts.session;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.ftp.FtpService;
import com.onwelo.practice.bts.service.BankAccountService;
import com.onwelo.practice.bts.service.BankService;
import com.onwelo.practice.bts.service.CsvService;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class SessionOutgoing {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(SessionOutgoing.class);
    private HashMap<String, ArrayList<Transfer>> hashedTransfers;
    private ArrayList<Transfer> transfers;

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CsvService csvService;

    @Autowired
    private FtpService ftpService;

    @Scheduled(cron = "0 * * * * *") // everyday at 12:00
    public void someMainMethod() {
        Logger.info("** Outgoing session starting **");

        getTransfers();
        if (!Objects.requireNonNull(transfers).isEmpty()) {
            hashTransfers();
            sendTransfers();
            updateBankAccounts();
            updateTransfers();
        }

        Logger.info("** Outgoing session ending **");
    }

    private void getTransfers() {
        transfers = (ArrayList<Transfer>) transferService.getTransfersByStatus(TransferStatus.REALIZED);

        if (transfers.isEmpty()) {
            Logger.info("Outgoing session: no transfers in this session");
        }
    }

    private void hashTransfers() {
        hashedTransfers = new HashMap<>();

        for (Transfer transfer : transfers) {
            String iban = BankService.getBankID(transfer.getAccountId().getAccountNo());
            ArrayList<Transfer> transferList;

            if (hashedTransfers.containsKey(iban)) {
                transferList = hashedTransfers.get(iban);
                if (transferList != null) {
                    transferList.add(transfer);
                    hashedTransfers.replace(iban, transferList);
                } else {
                    transferList = new ArrayList<>();
                    transferList.add(transfer);
                    hashedTransfers.replace(iban, transferList);
                }
            } else {
                transferList = new ArrayList<>();
                transferList.add(transfer);
                hashedTransfers.put(iban, transferList);
            }
        }

        /* test printing
        for (Map.Entry<String, ArrayList<Transfer>> entry : hashedTransfers.entrySet()) {
            System.out.println("\n IBAN: " + entry.getKey());
            for (Transfer transfer : entry.getValue())
                System.out.println("    " + transfer.toString());
        } */
    }

    private InputStream getFileInputStream(ArrayList<Transfer> transfers) {
        return new ByteArrayInputStream(csvService.getCsvFromTransfers(transfers).toString().getBytes());
    }

    private void sendTransfers() {
        for (Map.Entry<String, ArrayList<Transfer>> map : hashedTransfers.entrySet()) {

            InputStream inputStream = getFileInputStream(map.getValue());
            ftpService.createDirectory(map.getKey());

            if (ftpService.addFile(inputStream,
                    "/" + map.getKey() + "/transfers_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv")) {
                Logger.info("Outgoing session: successful upload csv to ftp");
            } else {
                Logger.debug("Outgoing session: failed upload csv to ftp");
            }
        }
    }

    private void updateBankAccounts() {
        Logger.info("Outgoing session: updating accounts");
        transfers.forEach(transfer -> {
            BankAccount bankAccount = transfer.getAccountId();
            bankAccount.setMoneyBlocked(bankAccount.getMoneyBlocked().subtract(transfer.getValue()));
            bankAccountService.updateBankAccount(bankAccount);
        });
    }

    private void updateTransfers() {
        Logger.info("Outgoing session: updating transfers");

        transfers.forEach(transfer -> {
            transfer.setStatus(TransferStatus.REALIZED);
            transferService.updateTransfer(transfer);
        });
    }
}
package com.onwelo.practice.bts.session;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.ftp.FtpService;
import com.onwelo.practice.bts.service.TransferService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class SessionIngoing {

    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(SessionIngoing.class);
    @Value("${bank.iban}")
    private static String bankDirectory;
    private ArrayList<Transfer> transferArrayList;
    @Autowired
    private TransferService transferService;
    @Autowired
    private FtpService ftpService;

    @Scheduled(cron = "0 0 3,9,15 * * *")
        // everyday at 03:00,09:00,15:00
    void startSessionIngoing() {
        if (ftpService.isConnected()) {
            retriveAllTransferFile();
        } else {
            alternativeSessionIngoingStart();
        }
    }

    private void alternativeSessionIngoingStart() {
        if (ftpService.openConnection()) {
            retriveAllTransferFile();
        }
    }

    private boolean retriveAllTransferFile() {

        try {
            transferArrayList = ftpService.retriveAllFiles("/"+bankDirectory);
            if (transferArrayList.isEmpty()) {
                return true;
            } else {
                addAllTransfers();
                return true;
            }
        } catch (IOException e) {
            Logger.debug("Problem with retrive transfers from FTP");
        }
        return false;
    }

    private void addAllTransfers() {
        transferArrayList.forEach(transfer -> transferService.addTransfer(transfer));
    }

}

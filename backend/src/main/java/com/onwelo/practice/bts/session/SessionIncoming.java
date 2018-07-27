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
public class SessionIncoming {

    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(SessionIncoming.class);
    @Value("${bank.iban}")
    private static String bankDirectory;
    private ArrayList<Transfer> transferArrayList;
    @Autowired
    private TransferService transferService;
    @Autowired
    private FtpService ftpService;

    @Scheduled(cron = "0 5 11 * * *")
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
            Logger.debug("bankDirectory->" + bankDirectory);
            Logger.debug("transferArrayList.isEmpty()->" +transferArrayList.isEmpty());
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
        transferArrayList.forEach( transfer ->  Logger.debug("bankDirectory->" + transfer));
    }

}

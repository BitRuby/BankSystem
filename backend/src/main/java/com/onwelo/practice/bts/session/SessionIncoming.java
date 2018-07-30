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
    private String bankDirectory;
    private ArrayList<Transfer> transferArrayList;
    @Autowired
    private TransferService transferService;
    @Autowired
    private FtpService ftpService;

    @Scheduled(cron = "0 23 9 * * *")
    void startSessionIngoing() throws IOException {
        if (ftpService.isConnected()) {
            retriveAllTransferFile();
        } else {
            alternativeSessionIngoingStart();
        }
    }

    private void alternativeSessionIngoingStart() {
        if (ftpService.openConnection()) {
            try {
                retriveAllTransferFile();
            } catch (IOException e) {
                Logger.debug("alternativeSessionIngoingStart - > " + e);
            }
        }
    }

    private boolean retriveAllTransferFile() throws IOException {

        Logger.debug("bankDirectory ->" + bankDirectory);


        Logger.debug("getFilesListFromDirectory(bankDirectoryPath)->" + ftpService.getFilesListFromDirectory("/" + bankDirectory));
        ftpService.getFilesListFromDirectory("/" + bankDirectory).stream().forEach(s -> {
            transferArrayList = ftpService.retriveAllFile(s);
            Logger.debug("transferArrayList.isEmpty() ->" + transferArrayList.isEmpty());
            if (!transferArrayList.isEmpty()) {
                addAllTransfers(s);
            }
        });
        return false;
    }

    private void addAllTransfers(String s) {
        Logger.debug("Start adding to db -> " + bankDirectory + " :: " + s);
        transferArrayList.forEach(transfer -> transferService.addTransfer(transfer));
        Logger.debug("Stop adding to db -> " + bankDirectory + " :: " + s);
    }

}

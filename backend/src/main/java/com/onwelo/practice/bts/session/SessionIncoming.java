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

    @Scheduled(cron = "0 0 3,9,15 * * *")
        // everyday at 3:00, 9:00, 15:00
    void startSessionIncoming() throws IOException {
        Logger.info("** Incoming session starting **");

        if (!ftpService.isConnected()) {
            ftpService.openConnection();
        }

        if (retrieveAllTransferFile()) {
            clearDirectory();
        }

        Logger.info("** Incoming session ending **");
    }

    private Boolean retrieveAllTransferFile() throws IOException {
        Logger.info("Incoming session: getting csv files from ftp");

        transferArrayList = null;
        ftpService.getFilesListFromDirectory("/" + bankDirectory).forEach(s -> {
            transferArrayList = ftpService.retriveAllFile(s);
            if (!transferArrayList.isEmpty()) {
                addAllTransfers(s);
            }
        });

        if (transferArrayList == null) {
            Logger.info("Incoming session: no transfers in this session");
            return false;
        } else {
            return true;
        }
    }

    private void addAllTransfers(String s) {
        Logger.info("Incoming session: adding transfers from bank [" + s.substring(6, 10) + "] to database... ");
        transferArrayList.forEach(transfer -> transferService.addTransfer(transfer));
    }

    private void clearDirectory() {
        Logger.info("Incoming session: clearing bank directory");

        try {
            ftpService.deleteAllFiles("/" + bankDirectory);
        } catch (IOException e) {
            Logger.debug(e.getMessage(), e);
        }
    }
}

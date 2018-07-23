package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
public class CsvService {
    private final String splitSign = ";";

    @Autowired
    TransferService transferService;

    @Autowired
    BankAccountService bankAccountService;

    // TODO getting csv from server & parsing into collection

    public File getCsvFile(String filename, TransferStatus transferStatus) {
        return createFile(new File(filename), transferStatus);
    }

    public File getCsvFile(String filename) {
        return createFile(new File(filename), TransferStatus.PENDING);
    }

    public ArrayList<Transfer> getTransfersFromCsv(File file) {
        // get file from sftp
        return parseFromCsv(readFile(file));
    }

    private File createFile(File file, TransferStatus transferStatus) {
        ArrayList<String> lines = parseToCsv(transferStatus);

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            lines.forEach(writer::append);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }

    private ArrayList<String> parseToCsv(TransferStatus transferStatus) {
        ArrayList<Transfer> transfers = (ArrayList<Transfer>) transferService.getTransfersByStatus(transferStatus);
        ArrayList<String> lines = new ArrayList<>();

        for (Transfer transfer : transfers) {
            lines.add(transfer.getCreateTime().toString() + splitSign + transfer.getTitle() + splitSign + transfer.getValue() +
                    splitSign + transfer.getAccountId().getAccountNo() + // owner
                    splitSign + transfer.getAccountNo() + "\n"); // target
        }

        return lines;
    }

    private ArrayList<String[]> readFile(File file) {
        ArrayList<String[]> transferLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transferLines.add(line.split(splitSign));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transferLines;
    }

    private ArrayList<Transfer> parseFromCsv(ArrayList<String[]> transferLines) {
        ArrayList<Transfer> transfers = new ArrayList<>();

        for (String[] string : transferLines) {
            transfers.add(new Transfer(string[1], Float.valueOf(string[2]), bankAccountService.getBankAccountByNumber(string[3]), string[2], TransferType.INCOMING));
        }
        return transfers;
    }
}

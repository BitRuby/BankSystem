package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
public class CsvService {
    @Autowired
    TransferService transferService;

    // TODO getting csv from server & parsing into collection

    public File getCsvFile(String filename, TransferStatus transferStatus) {
        return createFile(new File(filename), transferStatus);
    }

    public File getCsvFile(String filename) {
        return createFile(new File(filename), TransferStatus.PENDING);
    }

    public ArrayList<Transfer> getTransfersFromCsv(String filename) {
        return parseFromCsv(readFile(filename));
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
            lines.add(transfer.getCreateTime().toString() + "," + transfer.getTitle() + "," + transfer.getValue() +
                    "," + transfer.getAccountId().getAccountNo() + // owner
                    "," + transfer.getAccountNo() + "\n"); // target
        }

        return lines;
    }

    private ArrayList<String[]> readFile(String filename) {
        ArrayList<String[]> transferLines = new ArrayList<>();
        File file = new File(filename);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transferLines.add(line.split(","));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transferLines;
    }

    private ArrayList<Transfer> parseFromCsv(ArrayList<String[]> transferLines) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        return transfers;
    }
}

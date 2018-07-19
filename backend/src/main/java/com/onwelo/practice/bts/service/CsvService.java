package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
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

    private File createFile(File file, TransferStatus transferStatus) {
        ArrayList<String> lines = parseTransfers(transferStatus);

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            lines.forEach(writer::append);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }

    private ArrayList<String> parseTransfers(TransferStatus transferStatus) {
        ArrayList<Transfer> transfers = (ArrayList<Transfer>) transferService.getTransfersByStatus(transferStatus);
        ArrayList<String> lines = new ArrayList<>();

        for (Transfer transfer : transfers) {
            lines.add(transfer.getCreateTime().toString() + "," + transfer.getTitle() + "," + transfer.getValue() +
                    "," + transfer.getAccountId().getAccountNo() + // owner
                    "," + transfer.getAccountNo() + "\n"); // target
        }

        return lines;
    }
}

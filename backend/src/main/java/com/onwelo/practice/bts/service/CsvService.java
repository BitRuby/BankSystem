package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class CsvService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(CsvService.class);
    private static final String SplitSign = ";";

    @Autowired
    private BankAccountService bankAccountService;

    public File getCsvFromTransfers(ArrayList<Transfer> transfers, String filename) {
        return createFile(new File(filename), parseToCsv(transfers));
    }

    public ArrayList<Transfer> getTransfersFromCsv(File file) {
        // geting file from sftp
        return parseFromCsv(readFile(file));
    }

    private ArrayList<String> parseToCsv(ArrayList<Transfer> transfers) {
        ArrayList<String> lines = new ArrayList<>();

        for (Transfer transfer : transfers) {
            lines.add(transfer.getCreateTime().toString() + SplitSign +
                    transfer.getTitle() + SplitSign +
                    transfer.getValue() + SplitSign +
                    transfer.getAccountId().getAccountNo() + SplitSign + // owner
                    transfer.getAccountNo() + "\n"); // target
        }

        return lines;
    }

    private File createFile(File file, ArrayList<String> lines) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            lines.forEach(writer::append);
        } catch (FileNotFoundException e) {
            Logger.debug(e.getMessage());
        }

        return file;
    }

    private ArrayList<String[]> readFile(File file) {
        ArrayList<String[]> transferLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transferLines.add(line.split(SplitSign));
            }
        } catch (IOException e) {
            Logger.debug(e.getMessage());
        }

        return transferLines;
    }

    private ArrayList<Transfer> parseFromCsv(ArrayList<String[]> transferLines) {
        ArrayList<Transfer> transfers = new ArrayList<>();

        for (String[] string : transferLines) {
            transfers.add(new Transfer(string[1], BigDecimal.valueOf(Double.valueOf(string[2])), bankAccountService.getBankAccountByNumber(string[3]), string[4], TransferType.INCOMING));
        }

        return transfers;
    }
}

package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.Currency;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class CsvService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(CsvService.class);
    private static final String SplitSign = ";";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
            lines.add(transfer.getCreateTime().format(formatter) + SplitSign +
                    transfer.getTitle() + SplitSign +
                    transfer.getValue() + SplitSign +
                    transfer.getAccountId().getAccountNo() + SplitSign + // owner
                    transfer.getAccountNo() + SplitSign + // target
                    transfer.getCurrency() + "\n");
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
            transfers.add(new Transfer(string[1], // title
                    BigDecimal.valueOf(Double.valueOf(string[2])), // value
                    bankAccountService.getBankAccountByNumber(string[3]), // bankAccount
                    string[4], // accountNo
                    TransferStatus.APPROVED, // status
                    TransferType.INCOMING, // type
                    LocalDateTime.parse(string[0], formatter), // createTime
                    LocalDateTime.now(), // bookingDate
                    getCurrency(string[5]))); // currency
        }

        return transfers;
    }

    private Currency getCurrency(String currency) {
        switch (currency) {
            case "PLN":
                return Currency.PLN;
            case "EUR":
                return Currency.EUR;
        }

        return null;
    }
}

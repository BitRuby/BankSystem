package com.onwelo.practice.bts.utils;

import com.onwelo.practice.bts.entity.Transfer;

import java.io.File;
import java.util.List;

public class ImportCSVFile extends CSVFile {
    public ImportCSVFile(File file, String filename, String splitSign) {
        this.filename = filename;
        this.splitSign = splitSign;
    }

    public ImportCSVFile(File file, String filename) {
        this.filename = filename;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    @Override
    void parse() {
        String[] transfer;
        // TODO readLine & transfer.split(splitSign) & transfers.add(transfer)
    }
}

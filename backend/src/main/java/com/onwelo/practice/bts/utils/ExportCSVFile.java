package com.onwelo.practice.bts.utils;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;

import java.io.File;
import java.util.Collections;

public class ExportCSVFile extends CSVFile {
    public ExportCSVFile(String filename, String splitSign) {
        this.filename = filename;
        this.splitSign = splitSign;
    }

    public ExportCSVFile(String filename) {
        this.filename = filename;
    }

    public void readTransfers() { // readTransfers(enum state {})
        transferService = new TransferService();

        // temporary - need getTransferByState(State.PENDING)
        transfers = Collections.singletonList(transferService.getTransferById(1L));
    }

    public File getFile() {
        file = new File(filename);
        parse();
        return file;
    }

    // temp
    private void generateData() {
        readTransfers();
    }

    @Override
    void parse() {
        for (Transfer transfer : transfers) {
            // TODO build string from transfer object & add writeLine
        }
    }
}

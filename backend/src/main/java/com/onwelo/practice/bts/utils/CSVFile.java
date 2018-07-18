package com.onwelo.practice.bts.utils;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.service.TransferService;

import java.io.File;
import java.util.List;

abstract class CSVFile {
    String filename;
    String splitSign = ",";
    File file;

    TransferService transferService;
    List<Transfer> transfers;

    abstract void parse();
}

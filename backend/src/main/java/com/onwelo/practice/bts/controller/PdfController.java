package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.service.PdfService;
import com.onwelo.practice.bts.service.TransferService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @Autowired
    private TransferService transferService;

    @GetMapping("/get/{transferId}")
    public @ResponseBody byte[] getPdf(@PathVariable("transferId") Long transferId) throws IOException {
        File pdfFile = pdfService.createPdf(transferId + ".pdf", transferService.getTransferById(transferId));
        InputStream inputStream = getClass().getResourceAsStream(pdfFile.getName());
        return IOUtils.toByteArray(inputStream);
    }
}

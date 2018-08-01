package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.service.PdfService;
import com.onwelo.practice.bts.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @Autowired
    private TransferService transferService;

    @GetMapping("/download/{transferId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable("transferId") Long transferId, @RequestParam(value = "isRest", required = false, defaultValue = "true") Boolean isRest) {
        byte[] pdf = pdfService.createPDF(transferService.getTransferById(transferId), isRest);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; attachment; filename=transfer" + transferId + ".pdf")
                .body(pdf);
    }
}

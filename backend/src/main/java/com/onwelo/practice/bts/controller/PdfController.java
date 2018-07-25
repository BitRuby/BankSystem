package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.exceptions.NotFoundException;
import com.onwelo.practice.bts.service.PdfService;
import com.onwelo.practice.bts.service.TransferService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(PdfService.class);

    @Autowired
    private PdfService pdfService;

    @Autowired
    private TransferService transferService;

    @GetMapping("/download/{transferId}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable("transferId") Long transferId, @RequestParam(value = "isRest", required = false, defaultValue = "true") Boolean isRest) {
        Resource resource = pdfService.createPdfAsResource("transfer" + transferId + ".pdf", transferService.getTransferById(transferId), isRest);

        if (resource != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }

        else {
            throw new NotFoundException("Transaction confirmation file not found");
        }
    }
}

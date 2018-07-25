package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.service.PdfService;
import com.onwelo.practice.bts.service.TransferService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(PdfService.class);

    @Autowired
    private PdfService pdfService;

    @Autowired
    private TransferService transferService;

    @GetMapping("/download/{transferId}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable("transferId") Long transferId, HttpServletRequest request) {
        Resource resource = pdfService.createPdfAsResource("transfer" + transferId + ".pdf", transferService.getTransferById(transferId));

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            Logger.debug(e.getMessage(), e);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

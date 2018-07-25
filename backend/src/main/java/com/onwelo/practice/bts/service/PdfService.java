package com.onwelo.practice.bts.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferType;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(PdfService.class);

    public Resource createPdfAsResource(String filepathPdf, Transfer transfer, Boolean isRest) {
        if (transfer != null) {
            Document document = prepareDocument();
            PdfWriter writer = createWriter(document, filepathPdf);
            document.open();
            document = parseXHtml(document, writer, prepareContent(transfer, isRest));
            document.close();

            Path path = Paths.get(filepathPdf);
            Resource resource = null;

            try {
                resource = new UrlResource(path.toUri());
            } catch (MalformedURLException e) {
                Logger.debug(e.getMessage());
            }

            return resource;
        }

        else {
            return null;
        }
    }

    private Document prepareDocument() {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        return document;
    }

    private String prepareContent(Transfer transfer, Boolean isRest) {
        Path path;

        if (isRest) {
            path = Paths.get("backend/src/main/resources/pdf/pdfTheme.html");
        }

        else {
            path = Paths.get("src/main/resources/pdf/pdfTheme.html");
        }

        byte[] themeContent = null;

        try {
            themeContent = Files.readAllBytes(path);
        } catch (IOException e) {
            Logger.debug(e.getMessage());
        }

        assert themeContent != null;
        String pdfContent = new String(themeContent);
        
        if (transfer.getTransferType().equals(TransferType.INCOMING)) {
            pdfContent = pdfContent.replace("${date}", transfer.getBookingDate().toString());
            pdfContent = pdfContent.replace("${accountNoOwner}", transfer.getAccountNo());
            pdfContent = pdfContent.replace("${accountNoTarget}", transfer.getAccountId().getAccountNo());
        }

        if (transfer.getTransferType().equals(TransferType.OUTGOING)) {
            pdfContent = pdfContent.replace("${date}", transfer.getCreateTime().toString());
            pdfContent = pdfContent.replace("${accountNoOwner}", transfer.getAccountId().getAccountNo());
            pdfContent = pdfContent.replace("${accountNoTarget}", transfer.getAccountNo());
        }

        pdfContent = pdfContent.replace("${title}", transfer.getTitle());
        pdfContent = pdfContent.replace("${value}", transfer.getValue().toString());

        return pdfContent;
    }

    private PdfWriter createWriter(Document document, String filepathPdf) {
        PdfWriter writer = null;

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(filepathPdf));
        } catch (DocumentException | FileNotFoundException e) {
            Logger.debug(e.getMessage());
        }

        return writer;
    }

    private Document parseXHtml(Document document, PdfWriter writer, String pdfContent) {
        try {
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            InputStream inputStream = new ByteArrayInputStream(pdfContent.getBytes(StandardCharsets.UTF_8));
            worker.parseXHtml(writer, document, inputStream, null, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Logger.debug(e.getMessage());
        }

        return document;
    }
}

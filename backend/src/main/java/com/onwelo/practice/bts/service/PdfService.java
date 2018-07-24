package com.onwelo.practice.bts.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class PdfService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(PdfService.class);

    public Document createPdf(String filepathPdf, ArrayList<String> transferDetails) {
        Document document = prepareDocument();
        PdfWriter writer = createWriter(document, filepathPdf);
        document.open();
        document = parseXHtml(document, writer, prepareContent(transferDetails));
        document.close();
        return document;
    }

    private Document prepareDocument() {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        return document;
    }

    private String prepareContent(ArrayList<String> transferDetails) {
        Path path = Paths.get("utilities/pdfTheme.html");
        byte[] themeContent = null;

        try {
            themeContent = Files.readAllBytes(path);
        } catch (IOException e) {
            Logger.debug(e.getMessage());
        }

        assert themeContent != null;
        String pdfContent = new String(themeContent);

        pdfContent = pdfContent.replace("${date}", transferDetails.get(0));
        pdfContent = pdfContent.replace("${accountNoOwner}", transferDetails.get(1));
        pdfContent = pdfContent.replace("${accountNoTarget}", transferDetails.get(2));
        pdfContent = pdfContent.replace("${title}", transferDetails.get(3));
        pdfContent = pdfContent.replace("${value}", transferDetails.get(4));

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

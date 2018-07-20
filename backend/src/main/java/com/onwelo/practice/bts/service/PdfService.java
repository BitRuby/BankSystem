package com.onwelo.practice.bts.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class PdfService {
    public static final String FONT = "c:/windows/fonts/arial.ttf";
    private String pdfContent;

    public Document createPdf(String filepathPdf, ArrayList<String> transferDetails, String filename) {
        Document document = prepareDocuments(transferDetails, filename);
        document = parseXHtml(document,createWriter(document, filepathPdf), filename);
        document.close();
        return document;
    }

    private Document prepareDocuments(ArrayList<String> transferDetails, String filename) {
        Document document = new Document();
        document.setPageSize(PageSize.A4);

        pdfContent = "<html>\n" +
                "    <style>\n" +
                "        body { padding: 24px; color: #262626}\n" +
                "        p.normal-description {font-size: 16px}\n" +
                "        p.small-description {font-size: 10px}\n" +
                "        hr.line {border-top: 1px solid #ccc}\n" +
                "    </style>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\"></meta>" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>BankUI - twój najgorszy bank</h1>\n" +
                "        <p class=\"description\" style=\"margin-top: -20px\">Bankujesz - tracisz</p>\n" +
                "        <hr class=\"line\"></hr>\n" +
                "        <h2>Potwierdzenie płatności</h2>\n" +
                "        <p class=\"normal-description\">Data wysłania przelewu: <b>" + transferDetails.get(0) + "</b></p>\n" +
                "        <hr class=\"line\" align=\"left\" width=\"10%\"></hr>\n" +
                "        <p class=\"normal-description\">Rachunek źródłowy: <b>" + transferDetails.get(1) + "</b></p>\n" +
                "        <p class=\"normal-description\">Rachunek docelowy: <b>" + transferDetails.get(2) + "</b></p>\n" +
                "        <hr class=\"line\" align=\"left\" width=\"10%\"></hr>\n" +
                "        <p class=\"normal-description\">Tytuł: <b>" + transferDetails.get(3) + "</b></p>\n" +
                "        <p class=\"normal-description\">Kwota: <b>" + transferDetails.get(4) + "</b></p>\n" +
                "    </body>\n" +
                "</html>";

        /*
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            writer.write(pdfContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        /*
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8)) {
            writer.write(pdfContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        return document;
    }

    private PdfWriter createWriter(Document document, String filepathPdf) {
        PdfWriter writer = null;

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(filepathPdf));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        return writer;
    }

    private Document parseXHtml(Document document, PdfWriter writer, String filename) {
        try {
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            InputStream inputStream = new ByteArrayInputStream(pdfContent.getBytes(StandardCharsets.UTF_8));
            worker.parseXHtml(writer, document, inputStream, null, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }
}

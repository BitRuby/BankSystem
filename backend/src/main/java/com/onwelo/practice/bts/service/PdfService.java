package com.onwelo.practice.bts.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class PdfService {
    private String pdfContent;

    public Document createPdf(String filepathPdf, ArrayList<String> transferDetails) {
        Document document = prepareDocuments(transferDetails);
        PdfWriter writer = createWriter(document, filepathPdf);
        document.open();
        document = parseXHtml(document, writer);
        document.close();
        return document;
    }

    private Document prepareDocuments(ArrayList<String> transferDetails) {
        Document document = new Document();
        document.setPageSize(PageSize.A4);

        pdfContent = "<html>\n" +
                "    <style>\n" +
                "       body { \n" +
                "            padding: 24px; \n" +
                "            color: #262626;\n" +
                "            font-family: 'Times New Roman', Times, serif;}\n" +
                "        p.normal-description {font-size: 16px}\n" +
                "        p.small-description {font-size: 10px}\n" +
                "        hr.line {border-top: 1px solid #ccc}\n" +
                "    </style>\n" +
                "    <head>\n" +
                "        <meta http-equiv=\"content-type\" content=\"application/xhtml+xml; charset=UTF-8\"></meta>" +
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
                "        <p class=\"normal-description\">Kwota: <b>" + transferDetails.get(4) + " zł</b></p>\n" +
                "    </body>\n" +
                "</html>";

        return document;
    }

    private PdfWriter createWriter(Document document, String filepathPdf) {
        PdfWriter writer = null;

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(filepathPdf));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return writer;
    }

    private Document parseXHtml(Document document, PdfWriter writer) {
        try {
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            InputStream inputStream = new ByteArrayInputStream(pdfContent.getBytes(StandardCharsets.UTF_8));
            worker.parseXHtml(writer, document, inputStream, null, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }
}

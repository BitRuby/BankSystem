package com.onwelo.practice.bts.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {
    private String pathHTML;
    private String pathPDF;

    public PDFGenerator(String pathHTML, String pathPDF) {
        this.pathHTML = pathHTML;
        this.pathPDF = pathPDF;
    }

    public Document createPDF() {
        Document document = new Document();
        document.setPageSize(PageSize.A4);

        PdfWriter writer = readHTML(document);
        document.open();
        document = parseXHtml(document, writer);
        document.close();

        return document;
    }

    private PdfWriter readHTML(Document document) {
        PdfWriter writer = null;

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pathPDF));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return writer;
    }

    private Document parseXHtml(Document document, PdfWriter writer) {
        try {
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(pathHTML));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}

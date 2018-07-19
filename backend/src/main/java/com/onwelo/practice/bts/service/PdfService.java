package com.onwelo.practice.bts.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PdfService {
    public Document createPdf(String filepathPdf, String filepathHtml) {
        Document document = prepareDocument();
        document = parseXHtml(document,createWriter(document, filepathPdf), filepathHtml);
        document.close();
        return document;
    }

    public Document createPdf(String filepathPdf, File file) {
        Document document = prepareDocument();
        document = parseXHtml(document,createWriter(document, filepathPdf), file);
        document.close();
        return document;
    }

    private Document prepareDocument() {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
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

    private Document parseXHtml(Document document, PdfWriter writer, String filepathHtml) {
        try {
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filepathHtml));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private Document parseXHtml(Document document, PdfWriter writer, File file) {
        try {
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}

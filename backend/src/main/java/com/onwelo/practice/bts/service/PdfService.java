package com.onwelo.practice.bts.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferType;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.onwelo.practice.bts.service.CsvService.formatter;

@Service
public class PdfService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(PdfService.class);

    public byte[] createPDF(Transfer transfer, Boolean isRest) {
        byte[] pdf = null;

        if (transfer != null) {
            byte[] content = prepareContent(transfer, isRest).getBytes(StandardCharsets.UTF_8);

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ByteArrayInputStream is = new ByteArrayInputStream(content)) {

                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, baos);

                document.open();
                XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, StandardCharsets.UTF_8);
                document.close();

                pdf = baos.toByteArray();
            } catch (IOException | DocumentException e) {
                Logger.debug(e.getMessage(), e);
            }
        }

        return pdf;
    }

    private String prepareContent(Transfer transfer, Boolean isRest) {
        Path path;

        if (isRest) {
            path = Paths.get("backend/src/main/resources/pdf/pdfTheme.html");
        } else {
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
            pdfContent = pdfContent.replace("${date}", transfer.getBookingDate().format(formatter));
            pdfContent = pdfContent.replace("${accountNoOwner}", transfer.getAccountNo());
            pdfContent = pdfContent.replace("${accountNoTarget}", transfer.getAccountId().getAccountNo());
        }

        if (transfer.getTransferType().equals(TransferType.OUTGOING)) {
            pdfContent = pdfContent.replace("${date}", transfer.getCreateTime().format(formatter));
            pdfContent = pdfContent.replace("${accountNoOwner}", transfer.getAccountId().getAccountNo());
            pdfContent = pdfContent.replace("${accountNoTarget}", transfer.getAccountNo());
        }

        pdfContent = pdfContent.replace("${title}", transfer.getTitle());
        pdfContent = pdfContent.replace("${value}", transfer.getValue().toString());

        return pdfContent;
    }
}

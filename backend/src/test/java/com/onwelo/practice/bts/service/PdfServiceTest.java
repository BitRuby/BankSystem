package com.onwelo.practice.bts.service;

import com.itextpdf.text.Document;
import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class PdfServiceTest implements Extension {
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private PdfService pdfService;

    @AfterEach
    void deleteFiles() throws Exception {
        File pdfOutgoing = new File("pdfOutgoing.pdf");
        File pdfIncoming = new File("pdfIncoming.pdf");
        if (!pdfOutgoing.delete() && !pdfIncoming.delete()) {
            throw new Exception("failed deletion test files");
        }
    }

    @Test
    void getPdfOutgoing() {
        Transfer transfer = new Transfer("testowy tytuł przelewu wychodzącego", BigDecimal.valueOf(500),
                new BankAccount("123456", "Jan", "Kowalski", BigDecimal.valueOf(2000), BigDecimal.valueOf(0)),
                "987654321", TransferType.OUTGOING);
        transfer.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Document document = pdfService.createPdf("pdfOutgoing.pdf", transfer);
        assertNotNull(document);
    }

    @Test
    void getPdfIncoming() {
        Transfer transfer = new Transfer("testowy tytuł przelewu przychodzącego", BigDecimal.valueOf(500),
                new BankAccount("123456", "Jan", "Kowalski", BigDecimal.valueOf(2000), BigDecimal.valueOf(0)),
                "987654321", TransferType.INCOMING);
        transfer.setBookingDate(LocalDate.now());
        Document document = pdfService.createPdf("pdfIncoming.pdf", transfer);
        assertNotNull(document);
    }
}
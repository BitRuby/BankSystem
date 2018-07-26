package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", BigDecimal.valueOf(2000), BigDecimal.valueOf(0)),
                "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);
        transfer.setCreateTime(LocalDateTime.now());
        byte[] resource = pdfService.createPDF(transfer, false);
        assertNotNull(resource);
    }

    @Test
    void getPdfIncoming() {
        Transfer transfer = new Transfer("testowy tytuł przelewu przychodzącego", BigDecimal.valueOf(500),
                new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", BigDecimal.valueOf(2000), BigDecimal.valueOf(0)),
                "74 1050 1416 1000 0092 0379 3907", TransferType.INCOMING);
        transfer.setBookingDate(LocalDateTime.now());
        byte[] resource = pdfService.createPDF(transfer, false);
        assertNotNull(resource);
    }
}
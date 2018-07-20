package com.onwelo.practice.bts.service;

import com.itextpdf.text.Document;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PdfServiceTest implements Extension {
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private PdfService pdfService;

    @AfterEach
    void deleteFiles() throws Exception {
        // File fileHtml = new File("generatedPdf.html");
        File filePdf = new File("generatedPdf.pdf");
        /*
        if (!fileHtml.delete() && !filePdf.delete())
            throw new Exception("failed deletion test files");
            */
    }

    @Test
    void getPdfByName() {
        ArrayList<String> transferDetails = new ArrayList<>();
        transferDetails.add(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        transferDetails.add("123456");
        transferDetails.add("654321");
        transferDetails.add("Testowy tytuł przelewu");
        transferDetails.add("pięćset pieniędzy");
        Document document = pdfService.createPdf("generatedPdf.pdf", transferDetails, "generatedPdf.html");
        assertNotNull(document);
    }
}
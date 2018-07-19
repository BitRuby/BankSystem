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
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PdfServiceTest implements Extension {
    private File htmlFile;
    private String testPdfContent = "<html>\n" +
            "    <h1 style=\"color:darkslateblue\" align=\"center\">Bank service cośtam</h1>\n" +
            "    <h3>Test pdf</h3>\n" +
            "    <p align=\"justify\">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean tincidunt maximus est ut mattis. Sed feugiat ligula aliquam elementum vehicula. Nam id lacinia nulla. Vivamus elit enim, euismod eu viverra sit amet, sodales eu elit. Morbi efficitur mattis mauris, consequat tincidunt nulla sollicitudin eleifend. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus est purus, tincidunt vitae massa tempus, aliquam pretium felis. Morbi leo lacus, sagittis ullamcorper porttitor eu, ultrices eu nisi. Pellentesque gravida lectus ut sapien tempor interdum. Nulla id ex vitae tellus pellentesque suscipit sed vitae enim. Morbi efficitur et erat quis ultrices. Quisque vel eros nunc. Praesent at massa rhoncus, convallis nibh at, dictum est.</p>\n" +
            "</html>";

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private PdfService pdfService;

    @BeforeEach
    void createFiles() {
        try (PrintWriter out = new PrintWriter("test.html")) {
            out.println(testPdfContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void deleteFiles() throws Exception {
        File fileHtml = new File("test.html");
        File filePdf = new File("test.pdf");
        if (!fileHtml.delete() && !filePdf.delete())
            throw new Exception("failed deletion test files");
    }

    @Test
    void getPdfByName() {
        Document document = pdfService.createPdf("test.pdf", "test.html");
        assertNotNull(document);
    }

    @Test
    void getPdfByFile() {
        Document document = pdfService.createPdf("test.pdf", new File("test.html"));
        assertNotNull(document);
    }

    @Test
    void compareDocuments() {

    }
}
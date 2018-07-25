package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.service.BankAccountService;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class PdfControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PdfController pdfController;

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransferRepository transferRepository;

    @BeforeEach
    void prepareTransfer() {
        BankAccount bankAccount = new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", BigDecimal.valueOf(2000), BigDecimal.valueOf(0));
        bankAccountService.addBankAccount(bankAccount);

        Transfer transfer = new Transfer("testowy tytuł przelewu wychodzącego", BigDecimal.valueOf(500), bankAccount, "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);
        transferService.addTransfer(transfer);
    }

    @AfterEach
    void deleteTransfer() {
        transferRepository.deleteAll();
    }

    @Test
    void downloadPdf() {
    }
}

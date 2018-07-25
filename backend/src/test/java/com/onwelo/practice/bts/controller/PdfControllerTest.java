package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.service.BankAccountService;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PdfControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private Transfer transfer;
    @BeforeEach
    void prepareTransfer() {
        BankAccount bankAccount = new BankAccount("29116022020000000311935598", "Jan", "Kowalski", BigDecimal.valueOf(2000), BigDecimal.valueOf(0));
        bankAccountService.addBankAccount(bankAccount);

        transfer = new Transfer("testowy tytuł przelewu wychodzącego", BigDecimal.valueOf(500), bankAccount, "74105014161000009203793907", TransferType.OUTGOING);
        transfer.setCreateTime(new Timestamp(System.currentTimeMillis()));
        transferService.addTransfer(transfer);
    }

    @AfterEach
    void deleteTransfer() {
        transferRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }

    @Test
    void downloadPdf() throws Exception {
        mockMvc.perform(get("/pdf/download/{id}", transfer.getId().intValue())
        .param("isRest", "false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    void notFoundPdf() throws Exception {
        mockMvc.perform(get("/pdf/download/1000").
                param("isRest", "false"))
                .andExpect(status().isNotFound());
    }
}

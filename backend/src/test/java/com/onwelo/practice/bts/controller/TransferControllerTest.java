package com.onwelo.practice.bts.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.TransferRepository;
import com.onwelo.practice.bts.service.BankAccountService;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.TransferType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.onwelo.practice.bts.service.BankAccountServiceTest.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TransferControllerTest {

    private BankAccount bankAccount;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @BeforeAll
    public void initDB() {
        bankAccount = new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", bd1000, bd0);
        bankAccountService.addBankAccount(bankAccount);
    }

    @AfterAll
    public void cleanUp() {
        transferRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }

    @Test
    void indexTest() throws Exception {
        Transfer transfer = new Transfer("przelew", bd100, bankAccount, "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);
        for (int i = 0; i < 10; i++) {
            transferService.addTransfer(transfer);
        }

        mockMvc.perform(get("/transfers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].title", is("przelew")));
    }

    @Test
    void showTest() throws Exception {
        Transfer transfer = new Transfer("przelew", bd100, bankAccount, "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);
        transferService.addTransfer(transfer);

        mockMvc.perform(get("/transfers/{id}", transfer.getId().intValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(transfer.getId().intValue())))
                .andExpect(jsonPath("$.title", is("przelew")))
                .andExpect(jsonPath("$.value", is(transfer.getValue().doubleValue())))
                .andExpect(jsonPath("$.accountId", is(bankAccount.getId().intValue())))
                .andExpect(jsonPath("$.accountNo", is("74105014161000009203793907")))
                .andExpect(jsonPath("$.transferType", is(TransferType.OUTGOING.name())));

        mockMvc.perform(get("/transfers/{id}", 10000000))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    void createTest() throws Exception {
        Transfer transfer = new Transfer("przelew", bd100, bankAccount, "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);

        mockMvc.perform(
                post("/transfers")
                        .content(asJsonString(transfer))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.title", is("przelew")))
                .andExpect(jsonPath("$.value", is(transfer.getValue().doubleValue())))
                .andExpect(jsonPath("$.accountId", is(bankAccount.getId().intValue())))
                .andExpect(jsonPath("$.accountNo", is("74105014161000009203793907")))
                .andExpect(jsonPath("$.transferType", is(TransferType.OUTGOING.name())));

        // incorrect account No
        transfer.setAccountNo("7410501416100000920379390");
        mockMvc.perform(
                post("/transfers")
                        .content(asJsonString(transfer))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateTest() throws Exception {
        Transfer transfer = new Transfer("przelew", bd100, bankAccount, "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);
        transfer = transferService.addTransfer(transfer);

        transfer.setTitle("przelew 2");
        transfer.setAccountId(bankAccount);

        mockMvc.perform(
                put("/transfers/{id}", transfer.getId().intValue())
                        .content(asJsonString(transfer))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(transfer.getId().intValue())))
                .andExpect(jsonPath("$.title", is("przelew 2")))
                .andExpect(jsonPath("$.value", is(transfer.getValue().doubleValue())))
                .andExpect(jsonPath("$.accountId", is(bankAccount.getId().intValue())))
                .andExpect(jsonPath("$.accountNo", is("74105014161000009203793907")))
                .andExpect(jsonPath("$.transferType", is(TransferType.OUTGOING.name())));
    }

    //    @Test
    void deleteTest() throws Exception {
        Transfer transfer = new Transfer("przelew", bd100, bankAccount, "74 1050 1416 1000 0092 0379 3907", TransferType.OUTGOING);
        transferService.addTransfer(transfer);

        mockMvc.perform(
                delete("/transfers/{id}", transfer.getId().intValue())
                        .content(asJsonString(transfer))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(transfer.getId().intValue())))
                .andExpect(jsonPath("$.title", is("przelew")))
                .andExpect(jsonPath("$.value", is(transfer.getValue().doubleValue())))
                .andExpect(jsonPath("$.accountId", is(bankAccount.getId().intValue())))
                .andExpect(jsonPath("$.accountNo", is("74105014161000009203793907")))
                .andExpect(jsonPath("$.transferType", is(TransferType.OUTGOING.name())));

        mockMvc.perform(
                delete("/transfers/{id}", transfer.getId().intValue())
                        .content(asJsonString(transfer))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());

    }

    public static String asJsonString(Transfer transfer) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(transfer);
            Long id = transfer.getAccountId().getId();
            return jsonContent.replaceAll("\"accountId\":\\d+,", "\"accountId\":{\"id\":" + id + "},");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
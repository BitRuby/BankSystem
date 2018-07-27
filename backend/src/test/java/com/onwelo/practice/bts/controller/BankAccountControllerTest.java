package com.onwelo.practice.bts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwelo.practice.bts.entity.BankAccount;
import com.onwelo.practice.bts.repository.BankAccountRepository;
import com.onwelo.practice.bts.repository.BankRepository;
import com.onwelo.practice.bts.service.BankAccountService;
import com.onwelo.practice.bts.utils.TransferType;
import org.hamcrest.Matchers;
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

import java.math.BigDecimal;

import static com.onwelo.practice.bts.service.BankAccountServiceTest.bd0;
import static com.onwelo.practice.bts.service.BankAccountServiceTest.bd1000;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class BankAccountControllerTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private MockMvc mockMvc;

    private BankAccount bankAccount;

    @BeforeAll
    public void initDB() {
        bankAccount = bankAccountService.addBankAccount(new BankAccount("29 1160 2202 0000 0003 1193 5598", "Jan", "Kowalski", bd1000, bd0));
    }

    @AfterAll
    public void cleanUp() {
        bankAccountRepository.deleteAll();
    }

    @Test
    void indexTest() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void showTest() throws Exception {
        mockMvc.perform(get("/accounts/{id}", bankAccount.getId().intValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(bankAccount.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(bankAccount.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(bankAccount.getLastName())))
                .andExpect(jsonPath("$.moneyAmount", is(bankAccount.getMoneyAmount().doubleValue())))
                .andExpect(jsonPath("$.moneyBlocked", is(bankAccount.getMoneyBlocked().doubleValue())))
                .andExpect(jsonPath("$.accountNo", is(bankAccount.getAccountNo())));

        mockMvc.perform(get("/accounts/{id}", 100000000))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    void createTest() throws Exception {
        BankAccount bankAccount2 = new BankAccount("74 1050 1416 1000 0092 0379 3907", "Jan", "Nowak", bd1000, bd0);

        mockMvc.perform(
                post("/accounts")
                        .content(asJsonString(bankAccount2))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", is(bankAccount2.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(bankAccount2.getLastName())))
                .andExpect(jsonPath("$.moneyAmount", is(bankAccount2.getMoneyAmount().doubleValue())))
                .andExpect(jsonPath("$.moneyBlocked", is(bankAccount2.getMoneyBlocked().doubleValue())))
                .andExpect(jsonPath("$.accountNo", is(bankAccount2.getAccountNo())));

        mockMvc.perform(
                post("/accounts")
                        .content(asJsonString(bankAccount2))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void updateTest() throws Exception {
        BankAccount copy = bankAccount;

        bankAccount.setFirstName("Adam");
        bankAccount.setLastName("Nowak");
        bankAccount.setMoneyAmount(new BigDecimal(100000));

        // update
        mockMvc.perform(
                put("/accounts/{id}", bankAccount.getId().intValue())
                        .content(asJsonString(bankAccount))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", is(bankAccount.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(bankAccount.getLastName())))
                .andExpect(jsonPath("$.moneyAmount", is(bankAccount.getMoneyAmount().doubleValue())))
                .andExpect(jsonPath("$.moneyBlocked", is(bankAccount.getMoneyBlocked().doubleValue())))
                .andExpect(jsonPath("$.accountNo", is(bankAccount.getAccountNo())));

        // undo changes
        mockMvc.perform(
                put("/accounts/{id}", bankAccount.getId().intValue())
                        .content(asJsonString(copy))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", is(copy.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(copy.getLastName())))
                .andExpect(jsonPath("$.moneyAmount", is(copy.getMoneyAmount().doubleValue())))
                .andExpect(jsonPath("$.moneyBlocked", is(copy.getMoneyBlocked().doubleValue())))
                .andExpect(jsonPath("$.accountNo", is(copy.getAccountNo())));

        bankAccount = copy;
    }

    //    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(
                delete("/accounts/{id}", bankAccount.getId().intValue())
                        .content(asJsonString(bankAccount))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(bankAccount.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(bankAccount.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(bankAccount.getLastName())))
                .andExpect(jsonPath("$.moneyAmount", is(bankAccount.getMoneyAmount().doubleValue())))
                .andExpect(jsonPath("$.moneyBlocked", is(bankAccount.getMoneyBlocked().doubleValue())))
                .andExpect(jsonPath("$.accountNo", is(bankAccount.getAccountNo())));

        mockMvc.perform(
                delete("/accounts/{id}", bankAccount.getId().intValue())
                        .content(asJsonString(bankAccount))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    public static String asJsonString(BankAccount bankAccount) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
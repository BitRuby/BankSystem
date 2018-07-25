package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.entity.Bank;
import com.onwelo.practice.bts.repository.BankRepository;
import com.onwelo.practice.bts.service.BankService;
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

import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class BankControllerTest {

    @Autowired
    private BankService bankService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private MockMvc mockMvc;

    private List<Bank> banks;

    @BeforeAll
    public void initDB() {
        banks = new ArrayList<>() {{
            add(new Bank("Bank Millennium SA", "Millennium - Centrum Rozliczeniowe", "80-887", "Gdańsk", "ul. Wały Jagiellońskie 10/16", "11602202"));
            add(new Bank("ING Bank Śląski SA", "Oddział w Kielcach ul.Silniczna 26", "25-515", "Kielce", "ul. Silniczna 26", "10501416"));
        }};
        bankService.addAll(banks);
    }

    @AfterAll
    public void clearUp() {
        bankRepository.deleteAll();
    }

    @Test
    void getBankNameTest() throws Exception {
        mockMvc.perform(
                get("/banks/find-by-account-no/{accountNo}", "29 1160 2202 0000 0003 1193 5598"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.bank", is(banks.get(0).getName() + " - " + banks.get(0).getDepartment())));

        mockMvc.perform(
                get("/banks/find-by-account-no/{accountNo}", "74 1050 1416 1000 0092 0379 3907"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.bank", is(banks.get(1).getName() + " - " + banks.get(1).getDepartment())));

        mockMvc.perform(
                get("/banks/find-by-account-no/{accountNo}", "74 1050 1416 1000 0092 0379"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}
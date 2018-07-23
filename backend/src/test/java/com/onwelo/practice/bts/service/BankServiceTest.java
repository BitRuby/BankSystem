package com.onwelo.practice.bts.service;

import com.onwelo.practice.bts.entity.Bank;
import com.onwelo.practice.bts.exceptions.NotValidField;
import com.onwelo.practice.bts.repository.BankRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class BankServiceTest {

    @Autowired
    private BankService bankService;

    @Autowired
    private BankRepository bankRepository;

    @AfterEach
    public void cleanUp() {
        bankRepository.deleteAll();
    }

    @Test
    void addAll() {
        List<Bank> banks = new ArrayList<>() {{
            add(new Bank("Bank Millennium SA", "Millennium - Centrum Rozliczeniowe", "80-887", "Gdańsk", "ul. Wały Jagiellońskie 10/16", "11602202"));
            add(new Bank("ING Bank Śląski SA", "Oddział w Kielcach ul.Silniczna 26", "25-515", "Kielce", "ul. Silniczna 26", "10501416"));
        }};
        bankService.addAll(banks);

        assertEquals(2, bankRepository.findAll().size());
    }

    @Test
    public void getBank() {
        List<Bank> banks = new ArrayList<>() {{
            add(new Bank("Bank Millennium SA", "Millennium - Centrum Rozliczeniowe", "80-887", "Gdańsk", "ul. Wały Jagiellońskie 10/16", "11602202"));
            add(new Bank("ING Bank Śląski SA", "Oddział w Kielcach ul.Silniczna 26", "25-515", "Kielce", "ul. Silniczna 26", "10501416"));
        }};
        bankService.addAll(banks);

        String Millennium = "29 1160 2202 0000 0003 1193 5598";
        String ING = "74 1050 1416 1000 0092 0379 3907";

        System.out.println(bankService.getBank(ING));

        assertNotNull(bankService.getBank(Millennium));
        assertNotNull(bankService.getBank(ING));
        assertEquals("Bank Millennium SA", bankService.getBank(Millennium).getName());
        assertEquals("ING Bank Śląski SA", bankService.getBank(ING).getName());
        assertThrows(NotValidField.class, () -> bankService.getBank(""));
        assertThrows(NotValidField.class, () -> bankService.getBank("74 1050 1416 1000 0092 0379"));

        bankRepository.deleteAll();
    }
}
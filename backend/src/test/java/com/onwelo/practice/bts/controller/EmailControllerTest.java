package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.utils.EmailConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private EmailConfig emailConfig;

    @BeforeEach
    void prepareEmail() {
        emailConfig = new EmailConfig();
        emailConfig.setTo("onwelo-bts@outlook.com");
        emailConfig.setSubject("Tytuł wiadomości");
        emailConfig.setAccountNo("123456789");
        emailConfig.setTitle("Tytuł przelewu");
        emailConfig.setValue("pięćset pieniędzy");
        emailConfig.setTransferDate("wczoraj");
        emailConfig.setReason("domyśl się");
    }

    @Test
    void sendEmail() throws Exception {
        mockMvc.perform(
                post("/api/email")
                        .content(emailConfig.toJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(emailConfig.toJson()));
    }

}

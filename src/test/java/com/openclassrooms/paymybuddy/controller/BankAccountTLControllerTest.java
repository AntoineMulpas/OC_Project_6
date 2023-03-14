package com.openclassrooms.paymybuddy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.CheckBox;
import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BankAccountTLController.class)
class BankAccountTLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppAccountService appAccountService;

    @MockBean
    private BankTransactionService bankTransactionService;

    @Test
    void getBankRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bank-register"))
                .andExpect(status().isOk());
    }

    @Test
    void getBankTransferPage() throws Exception {
        when(appAccountService.getSoldOfAccount()).thenReturn(10.1);
        mockMvc.perform(MockMvcRequestBuilders.get("/bank-transfer"))
                .andExpect(status().isOk());
    }

    @Test
    void addMoneyToBankAccount() throws Exception {
        mockMvc.perform(post("/bank-transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper()
                        .writeValueAsString(new BankTransaction(200.0))))
                .andExpect(status().isOk());

    }
}


package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BankAccountTransactionController.class)
class BankAccountTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankTransactionService bankTransactionService;

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromAppAccountToABankAccount() throws Exception {
        when(bankTransactionService.makeANewTransactionFromAppAccountToBankAccount(10.1)).thenReturn(new BankTransaction());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/from?amount=10.1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromAppAccountToABankAccountShouldReturnExpectationFailed() throws Exception {
        when(bankTransactionService.makeANewTransactionFromAppAccountToBankAccount(10.1)).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/from?amount=10.1"))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromBankAccountToAppAccount() throws Exception {
        when(bankTransactionService.makeANewTransactionFromBankAccountToAppAccount(10.1)).thenReturn(new BankTransaction());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/to?amount=10.1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromBankAccountToAppAccountShouldReturnExpectationFailed() throws Exception {
        when(bankTransactionService.makeANewTransactionFromBankAccountToAppAccount(10.1)).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/to?amount=10.1"))
                .andExpect(status().isExpectationFailed());
    }
}
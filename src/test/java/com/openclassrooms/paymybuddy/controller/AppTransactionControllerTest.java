package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.service.AppTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AppTransactionController.class)
class AppTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppTransactionService appTransactionService;

    @Test
    void makeANewAppTransactionShouldReturnExpectationFailed() throws Exception {
        when(appTransactionService.makeANewAppTransaction(any(), any())).thenThrow(new RuntimeException());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/transaction/send")
                        .param("receiverId", "1")
                        .param("amount", "20.1"))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewAppTransactionS() throws Exception {
        when(appTransactionService.makeANewAppTransaction(1L, 10.1)).thenReturn(new AppTransaction(1L, 20.1));
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/transaction/send")
                                .queryParam("receiverId", "1")
                                .queryParam("amount", "20.1"))
                .andExpect(status().isOk());
    }
}
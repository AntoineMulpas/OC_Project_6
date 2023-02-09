package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.TransactionDTO;
import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.AppTransactionService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = TransferTLController.class)
class TransferTLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppAccountService appAccountService;

    @MockBean
    private BankTransactionService bankTransactionService;

    @MockBean
    private AppTransactionService appTransactionService;

    @Test
    void getTransferPage() throws Exception {
        when(appAccountService.getSoldOfAccount()).thenReturn(10.1);
        when(bankTransactionService.getListOfBankTransactionForCurrentUser()).thenReturn(List.of(new TransactionDTO("aze", "aze", "aze", 10.1)));
        when(appTransactionService.getListOfAppTransactionForCurrentUser()).thenReturn(List.of(new TransactionDTO("aze", "aze", "aze", 10.1)));
        mockMvc.perform(MockMvcRequestBuilders.get("/transfer"))
                .andExpect(status().isOk());
    }
}
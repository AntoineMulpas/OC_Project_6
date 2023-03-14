package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    @WithMockUser(value = "spring")
    void addBankAccountInformationShouldReturnExpectationFailed() throws Exception {
        when(bankAccountService.addingBankAccountInformation(any())).thenThrow(new IllegalArgumentException());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/bank_account/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"iban\": null, \"swift\":  null}")
        ).andExpect(status().isExpectationFailed());
    }

    @Test
    @WithMockUser(value = "spring")
    void addBankAccountInformation() throws Exception {
        when(bankAccountService.addingBankAccountInformation(any())).thenReturn(true);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/bank_account/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"iban\": \"fe\", \"swift\":  \"fez\"}")
        ).andExpect(status().isOk());
    }
}
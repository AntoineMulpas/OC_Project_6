package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class BankAccountIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountRepository bankAccountRepository;


    @Test
    @WithMockUser(value = "spring")
    void addBankAccountInformationShouldReturnExpectationFailed() throws Exception {
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
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(Optional.empty());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/bank_account/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"iban\": \"fe\", \"swift\":  \"fez\", \"accountNumber\":  \"45\", \"bankCode\": \"789\", \"counterCode\":  \"789\", \"ribKey\":  \"525\", \"username\":  \"ehauzi\"}")
        ).andExpect(status().isOk());
    }

}

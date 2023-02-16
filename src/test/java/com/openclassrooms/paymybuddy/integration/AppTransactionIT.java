package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.IdOfUserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class AppTransactionIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppTransactionRepository appTransactionRepository;

    @MockBean
    private AppAccountService appAccountService;

    @MockBean
    private IdOfUserAuthenticationService idOfUserAuthenticationService;


    @Test
    @WithMockUser(value = "spring")
    void makeANewAppTransactionShouldReturnExpectationFailed() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/transaction/send")
                                .param("receiverId", "1")
                                .param("amount", "20.1"))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewAppTransaction() throws Exception {
        when(idOfUserAuthenticationService.userIdExists(any())).thenReturn(true);
        when(appAccountService.getSoldOfAccount()).thenReturn(100.0);
        AppTransaction appTransaction = new AppTransaction(1L, 10.9);
        when(appTransactionRepository.save(any())).thenReturn(appTransaction);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/transaction/send")
                                .queryParam("receiverId", "1")
                                .queryParam("amount", "10.9"))
                .andExpect(status().isOk());
    }
}

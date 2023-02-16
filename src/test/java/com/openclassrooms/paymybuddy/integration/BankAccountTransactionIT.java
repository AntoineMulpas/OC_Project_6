package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import com.openclassrooms.paymybuddy.service.IdOfUserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class BankAccountTransactionIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankTransactionRepository bankTransactionRepository;

    @MockBean
    private IdOfUserAuthenticationService idOfUserAuthenticationService;

    @MockBean
    private AppAccountRepository appAccountRepository;

    @MockBean
    private FeeRepository feeRepository;

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromAppAccountToABankAccount() throws Exception {
        Optional <AppAccount> optionalAppAccount = Optional.of(new AppAccount(1L, 20.3, 1L));
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(optionalAppAccount);
        when(bankTransactionRepository.save(any())).thenReturn(new BankTransaction(1L, LocalDateTime.now(), 100.1));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/from?amount=10.1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromAppAccountToABankAccountShouldReturnExpectationFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/from?amount=10.1"))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromBankAccountToAppAccount() throws Exception {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        Optional<AppAccount> optionalAppAccount = Optional.of(new AppAccount(1L, 100.1, 1L));
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(optionalAppAccount);
        when(bankTransactionRepository.save(any())).thenReturn(new BankTransaction(1L, LocalDateTime.now(), 120.1));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/to?amount=10.1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromBankAccountToAppAccountShouldReturnExpectationFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/banktransaction/to?amount=10.1"))
                .andExpect(status().isExpectationFailed());
    }

}

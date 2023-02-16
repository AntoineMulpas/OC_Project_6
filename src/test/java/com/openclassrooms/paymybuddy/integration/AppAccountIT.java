package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.service.IdOfUserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class AppAccountIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppAccountRepository appAccountRepository;

    @MockBean
    private IdOfUserAuthenticationService idOfUserAuthenticationService;


    @Test
    @WithMockUser(value = "spring")
    void getSoldOfAccount() throws Exception {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(Optional.of(new AppAccount(1L, 10.1, 1L)));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/app_account/sold"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("10.1"));
    }

    @Test
    @WithMockUser(value = "spring")
    void getSoldOfAccountShouldReturnExpectationFailed() throws Exception {
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/app_account/sold"))
                .andExpect(status().isExpectationFailed());
    }

}

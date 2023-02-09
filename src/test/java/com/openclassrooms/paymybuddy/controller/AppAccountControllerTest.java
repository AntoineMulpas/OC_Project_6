package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AppAccountController.class)
class AppAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppAccountService appAccountService;

    @Test
    @WithMockUser(value = "spring")
    void getSoldOfAccount() throws Exception {
        when(appAccountService.getSoldOfAccount()).thenReturn(10.1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/app_account/sold"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("10.1"));
    }

    @Test
    @WithMockUser(value = "spring")
    void getSoldOfAccountShouldReturnExpectationFailed() throws Exception {
        when(appAccountService.getSoldOfAccount()).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/app_account/sold"))
                .andExpect(status().isExpectationFailed());
    }
}
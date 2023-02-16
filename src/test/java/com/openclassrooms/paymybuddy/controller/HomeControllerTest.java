package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BankAccountService bankAccountService;

    @MockBean
    private AppAccountService appAccountService;

    @Test
    void getHomePage() throws Exception {
        when(userService.isCurrentUserInformationSaved()).thenReturn(true);
        when(bankAccountService.bankAccountInformationArePresentOrNot()).thenReturn(true);
        when(appAccountService.getSoldOfAccount()).thenReturn(10.1);
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk());

    }
}
package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AppTransactionTLController.class)
class AppTransactionTLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRelationsService userRelationsService;

    @MockBean
    private AppAccountService appAccountService;

    @Test
    void getAppTransactionPage() throws Exception {
        when(userRelationsService.getListOfUserRelations()).thenReturn(List.of(new UserRelationsDTO(1L, "antoine", "antoine")));
        when(appAccountService.getSoldOfAccount()).thenReturn(10.1);
        mockMvc.perform(MockMvcRequestBuilders.get("/app-transfer"))
                .andExpect(status().isOk());
    }

    @Test
    void getAppTransactionPageForChosenFriend() throws Exception {
        when(userRelationsService.getListOfUserRelations()).thenReturn(List.of(new UserRelationsDTO(1L, "antoine", "antoine")));
        when(appAccountService.getSoldOfAccount()).thenReturn(10.1);
        mockMvc.perform(MockMvcRequestBuilders.get("/app-transfer-friend/1"))
                .andExpect(status().isOk());
    }
}
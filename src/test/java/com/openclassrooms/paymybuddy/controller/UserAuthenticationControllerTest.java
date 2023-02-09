package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.UserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserAuthenticationController.class)
class UserAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @Test
    void saveANewUserShouldReturnExpectationFailed() throws Exception {
        when(userAuthenticationService.saveAUser(any())).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authentication/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\" : \"antoine\", \"password\": \"antoine\"}"))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    void saveANewUser() throws Exception {
        when(userAuthenticationService.saveAUser(any())).thenReturn(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authentication/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"antoine\", \"password\": \"antoine\"}"))
                .andExpect(status().isOk());
    }
}
package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.UserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAuthenticationController.class)
class UserAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthenticationService userAuthenticationService;


    @Test
    void saveANewUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/authentication/add")
                        .param("username", "antoine@gmail.com")
                        .param("password", "password")
                        .param("firstName", "antoine")
                        .param("lastName", "antoine")
                )
                .andExpect(status().isOk());
    }
}
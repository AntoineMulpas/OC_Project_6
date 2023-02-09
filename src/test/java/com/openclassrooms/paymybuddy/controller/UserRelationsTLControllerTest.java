package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserRelationsTLController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRelationsTLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRelationsService userRelationsService;

    @Test
    void getUserRelationsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/friends"))
                .andExpect(status().isOk());
    }
}
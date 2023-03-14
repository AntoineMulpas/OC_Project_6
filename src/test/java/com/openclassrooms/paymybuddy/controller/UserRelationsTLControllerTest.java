package com.openclassrooms.paymybuddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserRelationsTLController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
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

    @Test
    void addNewUserRelation() throws Exception {
        User user = new User("Antoine", "Antoine", "antoine", LocalDate.now());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        when(userRelationsService.addAFriend(any())).thenReturn(new UserRelations(1L));
        mockMvc
                .perform(post("/friends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserRelation() throws Exception {
        mockMvc.perform(post("/friends/delete?id=1"))
                .andExpect(status().isOk());
    }
}
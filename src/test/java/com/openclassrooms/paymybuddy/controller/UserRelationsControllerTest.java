package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserRelationsController.class)
class UserRelationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRelationsService userRelationsService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser(value = "spring")
    void getListOfUserRelations() throws Exception {
        when(userRelationsService.getListOfUserRelations()).thenReturn(List.of(new UserRelationsDTO(1L, "antoine", "antoine")));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/relations/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void getListOfUserRelationsShouldReturnExpectationFailed() throws Exception {
        when(userRelationsService.getListOfUserRelations()).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/relations/list"))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    @WithMockUser(value = "spring")
    void addAFriend() throws Exception {
        when(userRelationsService.addAFriend(anyString())).thenReturn(new UserRelations(1L));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/relations/add?email=antoine"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void addAFriendShouldReturnExpectationFailed() throws Exception {
        when(userRelationsService.addAFriend("antoine")).thenThrow(new IllegalArgumentException());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/relations/add?email=antoine"))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    @WithMockUser(value = "spring")
    void deleteAFriend() throws Exception {
        when(userRelationsService.deleteAFriend(1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/relations/delete?friendId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void deleteAFriendShouldReturnExpectationFailed() throws Exception {
        when(userRelationsService.deleteAFriend(1L)).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/relations/delete?friendId=1"))
                .andExpect(status().isExpectationFailed());
    }
}
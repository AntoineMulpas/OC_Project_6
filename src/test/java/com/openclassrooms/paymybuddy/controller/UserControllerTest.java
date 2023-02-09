package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserDTO;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {

    }

    @WithMockUser(value = "spring")
    @Test
    void saveInformationOfAUser() throws Exception {
        when(userService.saveInformationOfAUser(any())).thenReturn(new User());
        mockMvc.perform(post("/api/v1/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\" : \"antoine\", \"lastName\": \"antoine\", \"birthday\": \"1992-03-29\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(value = "spring")
    @Test
    void saveInformationOfAUserShouldReturnExpectationFailed() throws Exception {
        when(userService.saveInformationOfAUser(any())).thenThrow(new RuntimeException());
        mockMvc.perform(post("/api/v1/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\" : \"antoine\", \"lastName\": \"antoine\", \"birthday\": \"1992-03-29\"}"))
                .andExpect(status().isExpectationFailed())
                .andReturn();
    }

    @WithMockUser(value = "spring")
    @Test
    void getCurrentUserInformation() throws Exception {
        UserDTO userDTO = new UserDTO();
        when(userService.getCurrentUserInformation()).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/information"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @WithMockUser(value = "spring")
    @Test
    void getCurrentUserInformationShouldReturnExpectationFailed() throws Exception {
        when(userService.getCurrentUserInformation()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/information"))
                .andExpect(status().isExpectationFailed());
    }

    @WithMockUser(value = "spring")
    @Test
    void isCurrentUserInformationSaved() throws Exception {
        when(userService.isCurrentUserInformationSaved()).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user/information-saved"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void isCurrentUserInformationSavedShouldReturnExpectationFailed() throws Exception {
        when(userService.isCurrentUserInformationSaved()).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/information-saved"))
                .andExpect(status().isExpectationFailed());
    }
}
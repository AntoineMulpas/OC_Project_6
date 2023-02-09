package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserDTO;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserTLController.class)
class UserTLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(value = "spring")
    void getUserProfilePage() throws Exception {
        UserDTO t = new UserDTO(
                "antoine",
                "antoine",
                LocalDate.now(),
                "antoine"
        );
        when(userService.getCurrentUserInformation()).thenReturn(t);
        mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void saveUserInformation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().isOk());
    }
}
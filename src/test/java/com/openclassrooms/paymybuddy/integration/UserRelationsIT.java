package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
import com.openclassrooms.paymybuddy.service.IdOfUserAuthenticationService;
import com.openclassrooms.paymybuddy.service.UserAuthenticationService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UserRelationsIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRelationsRepository userRelationsRepository;
    @MockBean
    private IdOfUserAuthenticationService idOfUserAuthenticationService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @Test
    void getListOfUserRelations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/friends"))
                .andExpect(status().isOk());
    }


    @Test
    void addAFriend() throws Exception {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(userAuthenticationService.findIdOfUserByUsername(any())).thenReturn(new UserAuthentication(1L, "fjizeo", "jfizo"));
        when(userRelationsRepository.findUserRelationsByFriendIdEqualsAndUserIdEquals(2L, 1L)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.post("/friends"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(value = "spring")
    void deleteAFriend() throws Exception {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(2L);
        when(userRelationsRepository.findUserRelationsByUserIdEqualsAndFriendIdEquals(2L, 1L)).thenReturn(Optional.of(new UserRelations(2L)));
        mockMvc.perform(MockMvcRequestBuilders.post("/friends/delete?id=1"))
                .andExpect(status().isOk());
    }

}

package com.openclassrooms.paymybuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import com.openclassrooms.paymybuddy.service.AppAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UserAuthenticationIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthRepository userAuthRepository;

    @Test
    @WithMockUser(value = "spring")
    void saveANewUser() throws Exception {
        UserAuthentication userAuthentication = new UserAuthentication("antoine", "password");
        Optional<UserAuthentication> optional = Optional.empty();
        when(userAuthRepository.findByUsernameEquals("antoine")).thenReturn(optional);
        when(userAuthRepository.save(any())).thenReturn(userAuthentication);
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userAuthentication)))
                .andExpect(status().isOk());
    }

}

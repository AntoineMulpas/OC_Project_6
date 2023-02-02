package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdOfUserAuthenticationServiceTest {

    private IdOfUserAuthenticationService underTest;

    @Mock
    private UserAuthRepository userAuthRepository;


    @BeforeEach
    void setUp() {
        underTest = new IdOfUserAuthenticationService(userAuthRepository);
    }

    @Test
    void getUserIdShouldThrow() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getName()).thenReturn("antoine");
        Optional<UserAuthentication> userAuthentication = Optional.empty();
        when(userAuthRepository.findByUsernameEquals("antoine")).thenReturn(userAuthentication);
        assertThrows(RuntimeException.class, () -> underTest.getUserId());
    }

    @Test
    void getUserId() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getName()).thenReturn("antoine");
        Optional<UserAuthentication> userAuthentication = Optional.of(
                new UserAuthentication(1L, "antoine", "password"));
        when(userAuthRepository.findByUsernameEquals("antoine")).thenReturn(userAuthentication);

        assertEquals(1L, underTest.getUserId());


    }

    @Test
    void userIdExists() {
        when(userAuthRepository.existsById(1L)).thenReturn(true);
        assertEquals(true, underTest.userIdExists(1L));
    }
}
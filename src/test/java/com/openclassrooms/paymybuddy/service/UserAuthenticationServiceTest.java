package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuth;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {

    private UserAuthenticationService underTest;
    @Mock
    private UserAuthRepository userAuthRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new UserAuthenticationService(userAuthRepository, passwordEncoder);
    }

    @Test
    void saveAUser() {
        UserAuth userToPass = new UserAuth("antoine@gmail.com", "password");
        underTest.saveAUser(userToPass);
        verify(userAuthRepository, times(1)).findByUsernameEquals("antoine@gmail.com");
    }

    @Test
    void saveAUserShouldThrowsNullPointerExceptionForPasswordIsNull() {
        UserAuth userToPass = new UserAuth("antoine@gmail.com", null);
        assertThrows(NullPointerException.class, () -> underTest.saveAUser(userToPass));
    }


    @Test
    void saveAUserShouldThrowUserNameNotFoundException() {
        UserAuth userToPass = new UserAuth("antoine@gmail.com", "password");
        when(userAuthRepository.findByUsernameEquals(userToPass.getUsername())).thenReturn(Optional.of(new UserAuth("antoine@gmail.com", "password")));
        assertThrows(RuntimeException.class, () -> underTest.saveAUser(userToPass));
    }
}
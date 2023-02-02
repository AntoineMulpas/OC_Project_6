package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {

    private UserAuthenticationService underTest;
    @Mock
    private UserAuthRepository userAuthRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AppAccountService appAccountService;

    @BeforeEach
    void setUp() {
        underTest = new UserAuthenticationService(userAuthRepository, passwordEncoder, appAccountService);
    }


    @Test
    void loadUserByUsername() {
        UserAuthentication userAuthentication = new UserAuthentication("antoine", "password");
        when(userAuthRepository.findByUsernameEquals("antoine")).thenReturn(Optional.of(userAuthentication));
        UserDetails userDetails = underTest.loadUserByUsername("antoine");
        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsernameShouldThrow() {
        Optional<UserAuthentication> optional = Optional.empty();
        when(userAuthRepository.findByUsernameEquals("antoine")).thenReturn(optional);
        assertThrows(UsernameNotFoundException.class, () -> underTest.loadUserByUsername("antoine"));
    }

    @Test
    void saveAUserShouldThrowWhenUserIsNull() {
        UserAuthentication userAuthentication = new UserAuthentication(null, "password");
        assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(userAuthentication));
    }

    @Test
    void saveAUserShouldThrowWhenUserIsEmpty() {
        UserAuthentication userAuthentication = new UserAuthentication("", "password");
        assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(userAuthentication));
    }

    @Test
    void saveAUserShouldThrowWhenPasswordIsNull() {
        UserAuthentication userAuthentication = new UserAuthentication("antoine", null);
        assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(userAuthentication));
    }

    @Test
    void saveAUserShouldThrowWhenPasswordIsEmpty() {
        UserAuthentication userAuthentication = new UserAuthentication("antoine", "");
        assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(userAuthentication));
    }

    @Test
    void saveUserShouldThrowIfUserNameAlreadyUsed() {
        UserAuthentication userAuthentication = new UserAuthentication("antoine", "password");
        Optional<UserAuthentication> optional = Optional.of(new UserAuthentication("antoine", "password"));
        when(userAuthRepository.findByUsernameEquals("antoine")).thenReturn(optional);
        assertThrows(RuntimeException.class, () -> underTest.saveAUser(userAuthentication));

    }

    @Test
    void saveAUser() {
        UserAuthentication userAuthentication = new UserAuthentication("antoine", "password");
        Optional<UserAuthentication> optional = Optional.empty();
        when(userAuthRepository.findByUsernameEquals("antoine")).thenReturn(optional);
        when(userAuthRepository.save(any())).thenReturn(userAuthentication);
        UserAuthentication userAuthentication1 = underTest.saveAUser(userAuthentication);
        assertNotNull(userAuthentication1);
    }

    @Test
    void findIdOfUserByUsernameShouldThrowWhenUserIsNotFound() {
        Optional<UserAuthentication> optional = Optional.empty();
        when(userAuthRepository.findByUsernameEquals(any())).thenReturn(optional);
        assertThrows(UsernameNotFoundException.class, () -> underTest.findIdOfUserByUsername("antoine"));
    }

    @Test
    void findIdOfUserByUsername() {
        Optional<UserAuthentication> optional = Optional.of(new UserAuthentication("antoine", "password"));
        when(userAuthRepository.findByUsernameEquals(any())).thenReturn(optional);
        UserAuthentication toCheck = underTest.findIdOfUserByUsername("antoine");
        assertNotNull(toCheck);

    }

    @Test
    void findUsernameByIdShouldThrowWhenFriendIdIsNotPresent() {
        Optional<UserAuthentication> optional = Optional.empty();
        when(userAuthRepository.findById(any())).thenReturn(optional);
        assertThrows(IllegalArgumentException.class, () -> underTest.findUsernameById(any()));
    }

    @Test
    void findByUsernameById() {
        Optional<UserAuthentication> optional = Optional.of(new UserAuthentication("antoine", "password"));
        when(userAuthRepository.findById(any())).thenReturn(optional);
        assertEquals("antoine", underTest.findUsernameById(any()));
    }
}
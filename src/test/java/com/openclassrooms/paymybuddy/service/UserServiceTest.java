package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService underTest;
    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
        user = new User(
                1L,
                "Antoine",
                "Antoine",
                "antoine@gmail.com",
                LocalDate.parse("1992-03-29")
        );
    }

    @Test
    void saveInformationOfAUser() {
        SecurityContextHolder.setContext(
                SecurityContextHolder.createEmptyContext()
        );
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("antoine@gmail.com", null)
        );

        Optional<User> optionalUser = Optional.empty();
        when(
                userRepository
                        .findUserByFirstNameEqualsAndLastNameEqualsAndBirthdayEquals(
                                "Antoine",
                                "Antoine",
                                LocalDate.parse("1992-03-29")))
                .thenReturn(optionalUser);

        assertEquals("Antoine", underTest.saveInformationOfAUser(user).getFirstName());
    }


    @Test
    void saveInformationOfAUserShouldThrow() {
        Optional<User> optionalUser = Optional.of(
                        new User(
                                1L,
                                "Antoine",
                                "Antoine",
                                "antoine@gmail.com",
                                LocalDate.parse("2022-03-29")
                        ));
        when(
                userRepository
                        .findUserByFirstNameEqualsAndLastNameEqualsAndBirthdayEquals(
                                "Antoine",
                                "Antoine",
                                LocalDate.parse("1992-03-29")))
                .thenReturn(optionalUser);

        assertThrows(RuntimeException.class, () -> underTest.saveInformationOfAUser(user));
    }

    @Test
    void getCurrentUserInformation() {
    }

    @Test
    void getUserInformation() {
        Optional <User> optionalUser = Optional.of(
                new User(
                        1L,
                        "Antoine",
                        "Antoine",
                        "Antoine",
                        LocalDate.now()));
        when(userRepository.findById(any())).thenReturn(
                optionalUser);

        User userInformationToAssert = underTest.getUserInformation(1L);
        assertEquals(optionalUser.get().getFirstName(), userInformationToAssert.getFirstName());
    }

    @Test
    void getUserInformationShowThrow() {
        Optional <User> optionalUser = Optional.empty();
        when(userRepository.findById(any())).thenReturn(
                optionalUser);
        assertThrows(UsernameNotFoundException.class, () -> underTest.getUserInformation(1L));
    }



}
package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.model.UserDTO;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Mock
    UserAuthRepository userAuthRepository;

    @Mock
    private SecurityContextHolder securityContextHolder;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, userAuthRepository);
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
        Authentication authentication = Mockito.mock(Authentication.class);
// Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Optional<User> optionalUser = Optional.of(
                new User(
                        1L,
                        "antoine",
                        "antoine",
                        "antoine",
                        LocalDate.now()
                )
        );

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("antoine");
        when(underTest.getCurrentUser()).thenReturn("antoine");
        when(userRepository.findUserByUserAuthenticationEquals("antoine")).thenReturn(optionalUser);
        UserDTO currentUserInformation = underTest.getCurrentUserInformation();
        assertEquals("antoine", currentUserInformation.getFirstName());
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
        Optional<UserAuthentication> userAuthentication = Optional.of(
                new UserAuthentication(
                        "Antoine",
                        "password"
                )
        );
        when(userAuthRepository.findById(any())).thenReturn(
                userAuthentication);
        when(userRepository.findUserByUserAuthenticationEquals("Antoine")).thenReturn(Optional.of(new User("Antoine", "Antoine", LocalDate.now())));

        User userInformationToAssert = underTest.getUserInformation(1L);
        assertEquals(optionalUser.get().getFirstName(), userInformationToAssert.getFirstName());
    }

    @Test
    void getUserInformationShowThrowUsernameNotFoundException() {
        Optional<UserAuthentication> userAuthentication = Optional.of(
                new UserAuthentication(
                        "Antoine",
                        "password"
                ));
        when(userAuthRepository.findById(any())).thenReturn(
                userAuthentication);
        assertThrows(UsernameNotFoundException.class, () -> underTest.getUserInformation(1L));
    }

    @Test
    void getUserInformationShowThrowRunTimeException() {
        Optional<UserAuthentication> userAuthentication = Optional.empty();
        when(userAuthRepository.findById(any())).thenReturn(
                userAuthentication);
        assertThrows(RuntimeException.class, () -> underTest.getUserInformation(1L));
    }


    @Test
    void isCurrentUserInformationSaved() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(underTest.getCurrentUser()).thenReturn("antoine");
        when(userRepository.findUserByUserAuthenticationEquals("antoine")).thenReturn(Optional.of(new User()));
        assertEquals(true, underTest.isCurrentUserInformationSaved());
    }

    @Test
    void getUserInformationByUsername() {
        when(userRepository.findUserByUserAuthenticationEquals("antoine")).thenReturn(
                Optional.of(
                        new User("antoine", "antoine", LocalDate.now())));
        assertEquals("antoine", underTest.getUserInformationByUsername("antoine").getFirstName());

    }


}
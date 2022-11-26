package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppAccountServiceTest {

    private AppAccountService underTest;
    @Mock
    private AppAccountRepository appAccountRepository;
    @Mock
    private IdOfUserAuthenticationService idOfUserAuthenticationService;


    @BeforeEach
    void setUp() {
        underTest = new AppAccountService(appAccountRepository, idOfUserAuthenticationService);
    }

    @Test
    void getSoldOfAccount() {
        Optional<AppAccount> optionalAppAccount = Optional.of(
                new AppAccount(1L, 0.0, 1L)
        );
        when(appAccountRepository.findById(1L)).thenReturn(optionalAppAccount);
        assertEquals(0.0, underTest.getSoldOfAccount(1L));
    }

    @Test
    void getSoldOfAccountThrow() {
        Optional<AppAccount> optionalAppAccount = Optional.empty();
        when(appAccountRepository.findById(1L)).thenReturn(optionalAppAccount);
        assertThrows(RuntimeException.class, () -> underTest.getSoldOfAccount(1L));
    }
}
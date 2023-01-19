package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppTransactionServiceTest {

    private AppTransactionService underTest;

    @Mock
    private AppTransactionRepository appTransactionRepository;
    @Mock
    private AppAccountService appAccountService;
    @Mock
    private IdOfUserAuthenticationService idOfUserAuthenticationService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        underTest = new AppTransactionService(appTransactionRepository, appAccountService, idOfUserAuthenticationService, userService);
    }


    @Test
    void getListOfAppTransactionForCurrentUserShouldReturnAListOfTransactionDTO() {
        underTest.getListOfAppTransactionForCurrentUser();

    }
}
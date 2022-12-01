package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppTransaction;
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
    private IdOfUserAuthenticationService idOfUserAuthenticationService;

    @BeforeEach
    void setUp() {
        underTest = new AppTransactionService(appTransactionRepository, idOfUserAuthenticationService);
    }

    @Test
    void makeANewAppTransaction() {
        when(idOfUserAuthenticationService.userIdExists(2L)).thenReturn(true);
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        AppTransaction appTransaction = new AppTransaction(2L, 10.3);
        underTest.makeANewAppTransaction(appTransaction);
    }

    @Test
    void makeANewAppTransactionShowThrowWhenReceiverIdIsNull() {
        AppTransaction appTransaction = new AppTransaction(null, 10.3);
        assertThrows(RuntimeException.class, () -> underTest.makeANewAppTransaction(appTransaction));
    }


    @Test
    void makeANewAppTransactionShouldThrowWhenAmountIsLessThanOne() {
        AppTransaction appTransaction = new AppTransaction(1L, -1.0);
        assertThrows(RuntimeException.class, () -> underTest.makeANewAppTransaction(appTransaction));

    }
}
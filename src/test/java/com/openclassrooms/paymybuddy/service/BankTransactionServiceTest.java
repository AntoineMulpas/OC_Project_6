package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankTransactionServiceTest {

    private BankTransactionService underTest;
    @Mock
    BankTransactionRepository bankTransactionRepository;
    @Mock
    IdOfUserAuthenticationService idOfUserAuthenticationService;
    @Mock
    AppAccountRepository appAccountRepository;

    @BeforeEach
    void setUp() {
        underTest = new BankTransactionService(bankTransactionRepository, idOfUserAuthenticationService, appAccountRepository);
    }

    @Test
    void makeANewTransactionFromAppAccountToBankAccount() {
        Optional<AppAccount> optionalAppAccount = Optional.of(new AppAccount(1L, 0.0, 1L));
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(optionalAppAccount);
        BankTransaction transactionToBePassed = new BankTransaction(10.3);
        underTest.makeANewTransactionFromAppAccountToBankAccount(transactionToBePassed.getAmount());
        verify(bankTransactionRepository, times(1)).save(any());
        verify(appAccountRepository, times(1)).save(any());
    }

    @Test
    void makeANewTransactionFromAppAccountToBankAccountShouldThrowWhenUserIdIsNull() {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(null);
        BankTransaction transactionToBePassed = new BankTransaction(10.3);
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromAppAccountToBankAccount(transactionToBePassed.getAmount()));
    }

    @Test
    void makeANewTransactionFromAppAccountToBankAccountShouldThrowWhenAmountIsInferiorToZero() {
        BankTransaction transactionToBePassed = new BankTransaction(-0.1);
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromAppAccountToBankAccount(transactionToBePassed.getAmount()));
    }

    @Test
    void makeANewTransactionFromAppAccountToBankAccountShouldThrowWhenAppAccountDoesNotExist() {
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(Optional.empty());
        BankTransaction transactionToBePassed = new BankTransaction(13.1);
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromAppAccountToBankAccount(transactionToBePassed.getAmount()));
    }

    @Test
    void makeANewTransactionFromBankAccountToAppAccount() {
        Optional<AppAccount> optionalAppAccount = Optional.of(new AppAccount(1L, 0.0, 1L));
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(optionalAppAccount);
        BankTransaction transactionToBePassed = new BankTransaction(10.3);
        underTest.makeANewTransactionFromBankAccountToAppAccount(transactionToBePassed.getAmount());
        verify(bankTransactionRepository, times(1)).save(any());
        verify(appAccountRepository, times(1)).save(any());
    }

    @Test
    void makeANewTransactionFromBankAccountToAppAccountShouldThrowWhenUserIdIsNull() {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(null);
        BankTransaction transactionToBePassed = new BankTransaction(10.3);
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromBankAccountToAppAccount(transactionToBePassed.getAmount()));
    }

    @Test
    void makeANewTransactionFromBankAccountToAppAccountShouldThrowWhenAmountIsInferiorToZero() {
        BankTransaction transactionToBePassed = new BankTransaction(-0.1);
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromBankAccountToAppAccount(transactionToBePassed.getAmount()));
    }

    @Test
    void makeANewTransactionFromBankAccountToAppAccountShouldThrowWhenAppAccountDoesNotExist() {
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(Optional.empty());
        BankTransaction transactionToBePassed = new BankTransaction(13.1);
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromBankAccountToAppAccount(transactionToBePassed.getAmount()));
    }


}
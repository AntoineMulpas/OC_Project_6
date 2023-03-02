package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
class BankTransactionServiceTest {

    private BankTransactionService underTest;
    @Mock
    BankTransactionRepository bankTransactionRepository;
    @Mock
    IdOfUserAuthenticationService idOfUserAuthenticationService;
    @Mock
    AppAccountRepository appAccountRepository;
    @Mock
    FeeRepository feeRepository;

    @BeforeEach
    void setUp() {
        underTest = new BankTransactionService(bankTransactionRepository, idOfUserAuthenticationService, appAccountRepository, feeRepository);
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromAppAccountToBankAccount() {
        Optional<AppAccount> optionalAppAccount = Optional.of(new AppAccount(1L, 20.3, 1L));
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(optionalAppAccount);
        when(bankTransactionRepository.save(any())).thenReturn(new BankTransaction(1L, LocalDateTime.now(), 100.1));
        underTest.makeANewTransactionFromAppAccountToBankAccount(10.1);
        verify(bankTransactionRepository, times(1)).save(any());
        verify(feeRepository, times(1)).save(any());
        verify(appAccountRepository, times(1)).save(any());
    }

    @Test
    void makeANewTransactionFromAppAccountToBankAccountShouldThrowIfAmountIsNegative() {
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromAppAccountToBankAccount(-100.3));
    }

    @Test
    @WithMockUser(value = "spring")
    void makeANewTransactionFromAppAccountToBankAccountShouldThrowIfNewSoldIsNegative() {
        Optional<AppAccount> optionalAppAccount = Optional.of(new AppAccount(1L, 20.3, 1L));
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(optionalAppAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.makeANewTransactionFromAppAccountToBankAccount(100.3));
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
    @WithMockUser(value = "spring")
    void makeANewTransactionFromBankAccountToAppAccount() {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        Optional<AppAccount> optionalAppAccount = Optional.of(new AppAccount(1L, 100.1, 1L));
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(optionalAppAccount);
        when(bankTransactionRepository.save(any())).thenReturn(new BankTransaction(1L, LocalDateTime.now(), 120.1));
        underTest.makeANewTransactionFromBankAccountToAppAccount(10.1);
        verify(bankTransactionRepository, times(1)).save(any());
        verify(feeRepository, times(1)).save(any());
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
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appAccountRepository.findAppAccountByUserIdEquals(1L)).thenReturn(Optional.empty());
        BankTransaction transactionToBePassed = new BankTransaction(13.1);
        assertThrows(RuntimeException.class, () -> underTest.makeANewTransactionFromBankAccountToAppAccount(transactionToBePassed.getAmount()));
    }

    @Test
    void getListOfBankTransactionForCurrentUserShouldThrowIfUserIdIsNull() {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> underTest.getListOfBankTransactionForCurrentUser());
    }

    @Test
    @WithMockUser(value = "spring")
    void getListOfBankTransactionForCurrentUser() {
        List<BankTransaction> mockedList = List.of(new BankTransaction(1L, LocalDateTime.now(), 12.2));
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(bankTransactionRepository.findTransactionByUserId(1L)).thenReturn(mockedList);
        assertEquals(1, underTest.getListOfBankTransactionForCurrentUser().size());
    }



}
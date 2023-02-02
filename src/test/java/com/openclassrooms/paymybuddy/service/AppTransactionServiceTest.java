package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.model.Fees;
import com.openclassrooms.paymybuddy.model.TransactionDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppTransactionServiceTest {

    @Autowired
    private AppTransactionService underTest;

    @Mock
    private AppTransactionRepository appTransactionRepository;
    @Mock
    private AppAccountService appAccountService;
    @Mock
    private IdOfUserAuthenticationService idOfUserAuthenticationService;
    @Mock
    private UserService userService;

    @Mock
    private FeeRepository feeRepository;


    @BeforeEach
    void setUp() {
        underTest = new AppTransactionService(appTransactionRepository, appAccountService, idOfUserAuthenticationService, userService, feeRepository);
    }

    /*
    ////WRITE TEST
     */
    @Test
    void getListOfAppTransactionForCurrentUserShouldReturnAListOfTransactionDTO() {
        List<AppTransaction> appTransactionList = List.of(
                new AppTransaction(1L, 2L, LocalDateTime.now(), 20.1),
                new AppTransaction(1L, 3L, LocalDateTime.now(), 30.1)
        );
        User user = new User("Antoine", "Antoine", LocalDate.now());
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(appTransactionRepository.findByIdOfCurrentUser(1L)).thenReturn(appTransactionList);
        when(userService.getUserInformation(any())).thenReturn(user);
        List <TransactionDTO> listOfAppTransactionForCurrentUser = underTest.getListOfAppTransactionForCurrentUser();
        assertNotNull(listOfAppTransactionForCurrentUser);

    }

    @Test
    void testMakeANewAppTransactionShouldThrowWhenAmountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> underTest.makeANewAppTransaction(1L, null));
    }

    @Test
    void testMakeANewAppTransactionShouldThrowWhenReceiverIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> underTest.makeANewAppTransaction(null, 10.1));
    }

    @Test
    void testMakeANewAppTransactionShouldThrowWhenReceiverIdDoesNotExist() {
        when(idOfUserAuthenticationService.userIdExists(10L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> underTest.makeANewAppTransaction(10L, 10.1));
    }

    @Test
    void testMakeANewAppTransactionShouldThrowWhenSoldIsInsufficient() {
        when(idOfUserAuthenticationService.userIdExists(any())).thenReturn(true);
        when(appAccountService.getSoldOfAccount()).thenReturn(1.0);
        assertThrows(IllegalArgumentException.class, () -> underTest.makeANewAppTransaction(10L, 10.1));
    }

    @Test
    void testMakeANewAppTransaction() {
        when(idOfUserAuthenticationService.userIdExists(any())).thenReturn(true);
        when(appAccountService.getSoldOfAccount()).thenReturn(100.0);
        AppTransaction appTransaction = new AppTransaction(1L, 10.9);
        when(appTransactionRepository.save(any())).thenReturn(appTransaction);
        underTest.makeANewAppTransaction(1L, 10.9);

        assertNotNull(appTransaction);
        verify(idOfUserAuthenticationService, times(1)).getUserId();
        verify(appAccountService, times(1)).getSoldOfAccount();
        verify(appAccountService, times(1)).updateSoldOfAppAccount(0L, -10.9);
        verify(appAccountService, times(1)).updateSoldOfAppAccount(1L, 10.9);
        verify(appTransactionRepository, times(1)).save(any(AppTransaction.class));
        verify(feeRepository, times(1)).save(any(Fees.class));
    }


}
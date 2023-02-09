package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    private BankAccountService underTest;
    @Mock
    private BankAccountRepository bankAccountRepository;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(
                SecurityContextHolder.createEmptyContext()
        );
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("antoine@gmail.com", null)
        );

        underTest = new BankAccountService(bankAccountRepository);
        bankAccount = new BankAccount(
                "1234567",
                "AB123AB123",
                "CB123",
                12,
                222,
                987,
                "antoine"
        );
    }

    @Test
    void addingBankAccountInformation() {
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals("antoine@gmail.com")).thenReturn(optionalBankAccount);
        underTest.addingBankAccountInformation(bankAccount);
        verify(bankAccountRepository, times(1)).save(any());
    }

    @Test
    void addingBankAccountInformationShouldThrowWhenBankAccountIsPresent() {
        Optional<BankAccount> optionalBankAccount = Optional.of(bankAccount);
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }

    @Test
    void addingBankAccountInformationShouldThrowWhenIbanIsNull() {
        bankAccount = new BankAccount(
                "1234567",
                null,
                "CB123",
                12,
                222,
                987,
                "antoine"
        );
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }

    @Test
    void addingBankAccountInformationShouldThrowWhenAccountNumberIsNull() {
        bankAccount = new BankAccount(
                null,
                "fhzeu",
                "CB123",
                12,
                222,
                987,
                "antoine"
        );
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }

    @Test
    void addingBankAccountInformationShouldThrowWhenSwiftIsNull() {
        bankAccount = new BankAccount(
                "fhuer",
                "fheu",
                null,
                12,
                222,
                987,
                "antoine"
        );
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }

    @Test
    void addingBankAccountInformationShouldThrowWhenBankCodeIsNull() {
        bankAccount = new BankAccount(
                "fhuze",
                "fhue",
                "fheuz",
                null,
                222,
                987,
                "antoine"
        );
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }

    @Test
    void addingBankAccountInformationShouldThrowWhenCounterCodeIsNull() {
        bankAccount = new BankAccount(
                "hfeu",
                "fhuezi",
                "fez",
                123,
                null,
                987,
                "antoine"
        );
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }

    @Test
    void addingBankAccountInformationShouldThrowWhenRibKeyIsNull() {
        bankAccount = new BankAccount(
                "fezfez",
                "fezfze",
                "gregze",
                12,
                123,
                null,
                "antoine"
        );
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertThrows(IllegalArgumentException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }

    @Test
    void bankAccountInformationArePresentOrNotShouldReturnFalse() {
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findBankAccountByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertEquals(false, underTest.bankAccountInformationArePresentOrNot());
    }

    @Test
    void bankAccountInformationArePresentOrNotShouldReturnTrue() {
        Optional<BankAccount> optionalBankAccount = Optional.of(new BankAccount());
        when(bankAccountRepository.findBankAccountByUsernameEquals(any())).thenReturn(optionalBankAccount);
        assertEquals(true, underTest.bankAccountInformationArePresentOrNot());
    }
}
package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
                987
        );
    }

    @Test
    void addingBankAccountInformation() {
        Optional<BankAccount> optionalBankAccount = Optional.empty();
        when(bankAccountRepository.findByUsernameEquals("antoine@gmail.com")).thenReturn(optionalBankAccount);
        assertEquals("Bank account's information saved with success.", underTest.addingBankAccountInformation(bankAccount));

    }

    @Test
    void addingBankAccountInformationShouldThrow() {
        Optional<BankAccount> optionalBankAccount = Optional.of(bankAccount);
        when(bankAccountRepository.findByUsernameEquals("antoine")).thenReturn(optionalBankAccount);
        assertThrows(RuntimeException.class, () -> underTest.addingBankAccountInformation(bankAccount));
    }
}
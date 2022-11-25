package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public String addingBankAccountInformation(BankAccount bankAccount) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional <BankAccount> optionalBankAccount = bankAccountRepository.findByUsernameEquals(username);
            if (optionalBankAccount.isEmpty()) {
                BankAccount bankAccountToSave = new BankAccount(
                        bankAccount.getAccountNumber(),
                        bankAccount.getIban(),
                        bankAccount.getSwift(),
                        bankAccount.getBankCode(),
                        bankAccount.getCounterCode(),
                        bankAccount.getRibKey(),
                        username
                );
                bankAccountRepository.save(bankAccountToSave);
                return "Bank account's information saved with success.";
            } else {
                throw new RuntimeException("Information already saved.");
            }
    }
}

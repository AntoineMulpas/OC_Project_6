package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                        null,
                        bankAccount.getIban(),
                        bankAccount.getSwift(),
                        null,
                        null,
                        null,
                        username
                );
                bankAccountRepository.save(bankAccountToSave);
                return "Bank account's information saved with success.";
            } else {
                throw new RuntimeException("Information already saved.");
            }
    }

    public Boolean bankAccountInformationArePresentOrNot() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <BankAccount> bankAccountByUsernameEquals = bankAccountRepository.findBankAccountByUsernameEquals(username);
        return bankAccountByUsernameEquals.isPresent();
    }
}

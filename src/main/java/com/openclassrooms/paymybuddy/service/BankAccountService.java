package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void addingBankAccountInformation(BankAccount bankAccount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (bankAccountRepository.findByUsernameEquals(username).isPresent()) {
            throw new IllegalArgumentException("Bank account already exists for user: " + username);
        }
        bankAccount.setUsername(username);
        bankAccountRepository.save(bankAccount);
    }


    public Boolean bankAccountInformationArePresentOrNot() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <BankAccount> bankAccountByUsernameEquals = bankAccountRepository.findBankAccountByUsernameEquals(username);
        return bankAccountByUsernameEquals.isPresent();
    }
}

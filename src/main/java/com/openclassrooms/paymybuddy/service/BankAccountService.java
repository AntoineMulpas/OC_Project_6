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

    public boolean addingBankAccountInformation(BankAccount bankAccount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<BankAccount> existingBankAccount = bankAccountRepository.findByUsernameEquals(username);

        if (existingBankAccount.isPresent()) {
            throw new IllegalArgumentException("Bank account already exists for user: " + username);
        }

        if (areBankDetailsValid(bankAccount)) {
            bankAccount.setUsername(username);
            bankAccountRepository.save(bankAccount);
            return true;
        } else {
            throw new IllegalArgumentException("Invalid bank details");
        }
    }

    private boolean areBankDetailsValid(BankAccount bankAccount) {
        return bankAccount.getIban() != null
                && bankAccount.getBankCode() != null
                && bankAccount.getAccountNumber() != null
                && bankAccount.getSwift() != null
                && bankAccount.getRibKey() != null
                && bankAccount.getCounterCode() != null;
    }



    public Boolean bankAccountInformationArePresentOrNot() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <BankAccount> bankAccountByUsernameEquals = bankAccountRepository.findBankAccountByUsernameEquals(username);
        return bankAccountByUsernameEquals.isPresent();
    }
}

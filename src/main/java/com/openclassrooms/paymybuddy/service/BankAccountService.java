package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.controller.AppAccountController;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(BankAccountService.class);


    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public boolean addingBankAccountInformation(BankAccount bankAccount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<BankAccount> existingBankAccount = bankAccountRepository.findByUsernameEquals(username);

        if (existingBankAccount.isPresent()) {
            logger.error("Bank account information are already present for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new IllegalArgumentException("Bank account already exists for user: " + username);
        }

        if (areBankDetailsValid(bankAccount)) {
            bankAccount.setUsername(username);
            bankAccountRepository.save(bankAccount);
            logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has successfully saved bank information.");
            return true;
        } else {
            logger.error("Invalid bank details for user " + SecurityContextHolder.getContext().getAuthentication().getName());
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

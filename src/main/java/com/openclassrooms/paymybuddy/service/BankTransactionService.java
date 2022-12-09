package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;

    private final AppAccountRepository appAccountRepository;

    @Autowired
    public BankTransactionService(BankTransactionRepository bankTransactionRepository, IdOfUserAuthenticationService idOfUserAuthenticationService, AppAccountRepository appAccountRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
        this.appAccountRepository = appAccountRepository;
    }

    public BankTransaction makeANewTransactionFromAppAccountToBankAccount(BankTransaction bankTransaction) {
        if (bankTransaction.getAmount() > 0) {
            Long userId = idOfUserAuthenticationService.getUserId();
            if (userId != null) {
                Optional <AppAccount> appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId);
                if (appAccount.isPresent()) {
                    Double soldOfAppAccount = appAccount.get().getSold();
                    Double newSoldOfAppAccount = soldOfAppAccount - bankTransaction.getAmount();
                    appAccount.get().setSold(newSoldOfAppAccount);
                    appAccountRepository.save(appAccount.get());

                    BankTransaction bankTransactionToSave = new BankTransaction(
                            userId,
                            LocalDateTime.now(),
                            bankTransaction.getAmount()
                    );
                    return bankTransactionRepository.save(bankTransactionToSave);
                }
                else {
                    throw new RuntimeException("App Account does not exist.");
                }
            } else {
                throw new RuntimeException("User id does not exist.");
            }
        } else {
            throw new RuntimeException("Amount is not correct.");
        }
    }

    public BankTransaction makeANewTransactionFromBankAccountToAppAccount(BankTransaction bankTransaction) {
        if (bankTransaction.getAmount() > 0) {
            Long userId = idOfUserAuthenticationService.getUserId();
            if (userId != null) {
                Optional <AppAccount> appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId);
                if (appAccount.isPresent()) {
                    Double soldOfAppAccount = appAccount.get().getSold();
                    Double newSoldOfAppAccount = soldOfAppAccount + bankTransaction.getAmount();
                    appAccount.get().setSold(newSoldOfAppAccount);
                    appAccountRepository.save(appAccount.get());

                    BankTransaction bankTransactionToSave = new BankTransaction(
                            userId,
                            LocalDateTime.now(),
                            bankTransaction.getAmount()
                    );
                    return bankTransactionRepository.save(bankTransactionToSave);
                }
                else {
                    throw new RuntimeException("App Account does not exist.");
                }
            } else {
                throw new RuntimeException("User id does not exist.");
            }
        } else {
            throw new RuntimeException("Amount is not correct.");
        }
    }
}

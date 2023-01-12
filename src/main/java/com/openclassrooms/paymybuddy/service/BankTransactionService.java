package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.BankTransactionDTO;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

    public BankTransaction makeANewTransactionFromAppAccountToBankAccount(Double amount) {
        if (amount > 0) {
            Long userId = idOfUserAuthenticationService.getUserId();
            if (userId != null) {
                Optional <AppAccount> appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId);
                if (appAccount.isPresent()) {
                    Double soldOfAppAccount = appAccount.get().getSold();
                    Double newSoldOfAppAccount = soldOfAppAccount - amount;
                    if (newSoldOfAppAccount > 0) {
                        appAccount.get().setSold(newSoldOfAppAccount);
                        appAccountRepository.save(appAccount.get());

                        BankTransaction bankTransactionToSave = new BankTransaction(
                                userId,
                                LocalDateTime.now(),
                                -amount
                        );
                        return bankTransactionRepository.save(bankTransactionToSave);
                    } else {
                        throw new RuntimeException("Your AppAccount cannot have a negative sold.");
                    }
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

    public BankTransaction makeANewTransactionFromBankAccountToAppAccount(Double amount) {
        if (amount > 0) {
            Long userId = idOfUserAuthenticationService.getUserId();
            if (userId != null) {
                Optional <AppAccount> appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId);
                if (appAccount.isPresent()) {
                    Double soldOfAppAccount = appAccount.get().getSold();
                    Double newSoldOfAppAccount = soldOfAppAccount + amount;
                    appAccount.get().setSold(newSoldOfAppAccount);
                    appAccountRepository.save(appAccount.get());

                    BankTransaction bankTransactionToSave = new BankTransaction(
                            userId,
                            LocalDateTime.now(),
                            amount
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

    public List<BankTransactionDTO> getListOfBankTransactionForCurrentUser() {
        Long userId= idOfUserAuthenticationService.getUserId();
        List <BankTransaction> transactionByUserId = bankTransactionRepository.findTransactionByUserId(userId);
        List<BankTransactionDTO> listToReturn = new ArrayList <>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        transactionByUserId.forEach(bankTransaction -> listToReturn.add(new BankTransactionDTO(bankTransaction.getDate().format(formatter), "AppAccount", "BankAccount", bankTransaction.getAmount())));
        return listToReturn;
    }

}

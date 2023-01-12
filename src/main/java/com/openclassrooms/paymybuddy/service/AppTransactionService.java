package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppTransactionService {

    private final AppTransactionRepository appTransactionRepository;
    private final AppAccountRepository appAccountRepository;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;

    @Autowired
    public AppTransactionService(AppTransactionRepository appTransactionRepository, AppAccountRepository appAccountRepository, IdOfUserAuthenticationService idOfUserAuthenticationService) {
        this.appTransactionRepository = appTransactionRepository;
        this.appAccountRepository = appAccountRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
    }

/*
/// ADD UPDATED OF SOLD ACCOUNT
 */
    public AppTransaction makeANewAppTransaction(Long receiverId, Double amount) {
            if (receiverId != null && amount != null && amount > 0) {
                if (idOfUserAuthenticationService.userIdExists(receiverId)) {
                    Long senderId = idOfUserAuthenticationService.getUserId();
                    AppTransaction transactionToSave = new AppTransaction(
                            senderId,
                            receiverId,
                            LocalDateTime.now(),
                            amount
                    );
                    return appTransactionRepository.save(transactionToSave);
                } else {
                    throw new RuntimeException("Receiver id: " + receiverId + " does not exist.");
                }
            } else {
                throw new RuntimeException("An error occurred.");
            }
    }
}

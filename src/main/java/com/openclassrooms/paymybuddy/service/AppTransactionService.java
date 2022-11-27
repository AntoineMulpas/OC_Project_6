package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppTransactionService {

    private final AppTransactionRepository appTransactionRepository;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;

    @Autowired
    public AppTransactionService(AppTransactionRepository appTransactionRepository, IdOfUserAuthenticationService idOfUserAuthenticationService) {
        this.appTransactionRepository = appTransactionRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
    }


    public AppTransaction makeANewAppTransaction(AppTransaction appTransaction) {
            if (appTransaction.getReceiverId() != null && appTransaction.getAmount() != null) {
                Long senderId = idOfUserAuthenticationService.getUserId();
                AppTransaction transactionToSave = new AppTransaction(
                        senderId,
                        appTransaction.getReceiverId(),
                        LocalDateTime.now(),
                        appTransaction.getAmount()
                );
                return appTransactionRepository.save(transactionToSave);
            } else {
                throw new RuntimeException();
            }
    }
}

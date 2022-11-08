package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppTransactionService {

    private final AppTransactionRepository appTransactionRepository;

    @Autowired
    public AppTransactionService(AppTransactionRepository appTransactionRepository) {
        this.appTransactionRepository = appTransactionRepository;
    }



}

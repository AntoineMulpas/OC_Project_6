package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppAccountService {

    private final AppAccountRepository appAccountRepository;

    @Autowired
    public AppAccountService(AppAccountRepository appAccountRepository) {
        this.appAccountRepository = appAccountRepository;
    }

    public Double getSoldOfAccount(Long id) {
        Optional <AppAccount> appAccount = appAccountRepository.findById(id);
        if (appAccount.isPresent()) {
            return appAccount.get().getSold();
        } else {
            throw new RuntimeException("An error occurred.");
        }
    }
}

package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppAccountService {

    private final AppAccountRepository appAccountRepository;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;

    @Autowired
    public AppAccountService(AppAccountRepository appAccountRepository, IdOfUserAuthenticationService idOfUserAuthenticationService) {
        this.appAccountRepository = appAccountRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
    }

    public Double getSoldOfAccount(Long id) {
        Optional <AppAccount> appAccount = appAccountRepository.findById(id);
        if (appAccount.isPresent()) {
            return appAccount.get().getSold();
        } else {
            throw new RuntimeException("An error occurred.");
        }
    }

    public AppAccount createAppAccount() {
        try {
            Long userId = idOfUserAuthenticationService.getUserId();
            AppAccount appAccount = new AppAccount(
                    0.0,
                    userId
            );
            appAccountRepository.save(appAccount);
            return appAccount;
        } catch (RuntimeException e) {
            return null;
        }
    }
}

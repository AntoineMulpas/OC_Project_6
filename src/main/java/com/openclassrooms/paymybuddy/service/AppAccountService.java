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

    public Double getSoldOfAccount() {
        Long id = idOfUserAuthenticationService.getUserId();
        Optional <AppAccount> appAccount = appAccountRepository.findAppAccountByUserIdEquals(id);
        if (appAccount.isPresent()) {
            return appAccount.get().getSold();
        } else {
            throw new RuntimeException("An error occurred while fetching the sold.");
        }
    }

    public AppAccount createAppAccount(Long id) {
            AppAccount appAccount = new AppAccount(
                    0.0,
                    id
            );
            appAccountRepository.save(appAccount);
            return appAccount;
    }
}

package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@Transactional
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
            return round(appAccount.get().getSold());
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

    public AppAccount updateSoldOfAppAccount(Long userId, double amount) {
        AppAccount appAccountToUpdate = appAccountRepository.findAppAccountByUserIdEquals(userId)
                .orElseThrow(() -> new RuntimeException("Cannot update account with id: " + userId));
        appAccountToUpdate.setSold(appAccountToUpdate.getSold() + amount);
        return appAccountRepository.save(appAccountToUpdate);
    }

    private double round(double value) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

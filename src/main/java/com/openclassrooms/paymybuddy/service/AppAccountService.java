package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final Logger logger = LogManager.getLogger(AppAccountService.class);


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
            logger.error("An error occurrend while getting sold of account. AppAccount for user " + SecurityContextHolder.getContext().getAuthentication().getName() + " is not present");
            throw new RuntimeException("An error occurred while fetching the sold.");
        }
    }

    public AppAccount createAppAccount(Long id) {
            AppAccount appAccount = new AppAccount(
                    0.0,
                    id
            );
            appAccountRepository.save(appAccount);
            logger.info("App account for user " + SecurityContextHolder.getContext().getAuthentication().getName() + " has been created.");
            return appAccount;
    }

    public AppAccount updateSoldOfAppAccount(Long userId, double amount) {
        AppAccount appAccountToUpdate = appAccountRepository.findAppAccountByUserIdEquals(userId)
                .orElseThrow(() -> {
                    logger.error("An error occurred while updating sold of account for user " + SecurityContextHolder.getContext().getAuthentication().getName() + ". AppAccount has not been found");
                    return new RuntimeException("Cannot update account with id: " + userId);
                });
        appAccountToUpdate.setSold(appAccountToUpdate.getSold() + amount);
        logger.info("Sold of account has been updated for user " + SecurityContextHolder.getContext().getAuthentication().getName());
        return appAccountRepository.save(appAccountToUpdate);
    }

    private double round(double value) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

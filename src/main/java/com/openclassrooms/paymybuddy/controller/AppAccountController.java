package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app_account")
public class AppAccountController {

    private final AppAccountService appAccountService;

    private static final Logger logger = LogManager.getLogger(AppAccountController.class);


    @Autowired
    public AppAccountController(AppAccountService appAccountService) {
        this.appAccountService = appAccountService;
    }

    @GetMapping("/sold")
    public ResponseEntity<Double> getSoldOfAccount(
    ) {
        try {
            Double soldOfAccount = appAccountService.getSoldOfAccount();
            logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " requested sold of account.");
            return ResponseEntity.ok().body(soldOfAccount);
        } catch (RuntimeException e) {
            logger.error("An error occurred while getting sold of account for user: " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(0.0);
        }
    }

}

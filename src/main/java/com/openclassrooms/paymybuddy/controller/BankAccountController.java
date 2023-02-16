package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank_account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    private static final Logger logger = LogManager.getLogger(BankAccountController.class);


    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBankAccountInformation(
            @RequestBody BankAccount bankAccount
            ) {
        try {
            bankAccountService.addingBankAccountInformation(bankAccount);
            logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has added bank account information.");
            return ResponseEntity.ok("Account created.");
        } catch (RuntimeException e) {
            logger.error("An error occurred while adding bank account information for user " + SecurityContextHolder.getContext().getAuthentication().getName() + ". "  + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.toString());
        }
    }




}

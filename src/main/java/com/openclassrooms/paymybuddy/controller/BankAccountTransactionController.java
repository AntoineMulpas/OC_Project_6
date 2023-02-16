package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/banktransaction")
public class BankAccountTransactionController {

    private final BankTransactionService bankTransactionService;

    private static final Logger logger = LogManager.getLogger(BankAccountTransactionController.class);


    @Autowired
    public BankAccountTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @PostMapping("/from")
    public ResponseEntity <String> makeANewTransactionFromAppAccountToABankAccount(
            @RequestParam Double amount
    ) {
        try {
            bankTransactionService.makeANewTransactionFromAppAccountToBankAccount(amount);
            logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has made a new transaction from AppAccount to BankAccount.");
            return ResponseEntity.ok().body("Transaction to bank of " + amount + " made with success.");
        } catch (RuntimeException e) {
            logger.error("An error occurred while making transaction from AppAccount to BankAccount for user " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("An error occurred for transaction to bank of " + amount + ". " + e);

        }
    }

    @PostMapping("/to")
    public ResponseEntity<String> makeANewTransactionFromBankAccountToAppAccount(
            @RequestParam Double amount
    ) {
        try {
            bankTransactionService.makeANewTransactionFromBankAccountToAppAccount(amount);
            logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has made a new transaction from BankAccount to AppAccount.");
            return ResponseEntity.ok().body("Transaction to app of " + amount + " made with success.");
        } catch (RuntimeException e) {
            logger.error("An error occurred while making transaction from BankAccount to AppAccount for user " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("An error occurred for transaction to app of " + amount + ". " + e);

        }
    }
}

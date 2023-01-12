package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/banktransaction")
public class BankAccountTransactionController {

    private final BankTransactionService bankTransactionService;

    @Autowired
    public BankAccountTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @PostMapping("/from")
    public ResponseEntity <String> makeANewTransactionFromAppAccountToABankAccount(
            @RequestParam Double amount
    ) {
        try {
            System.out.println("has been called");

            bankTransactionService.makeANewTransactionFromAppAccountToBankAccount(amount);
            return ResponseEntity.ok().body("Transaction to bank of " + amount + " made with success.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("An error occurend for transaction to bank of " + amount + ". " + e);

        }
    }

    @PostMapping("/to")
    public ResponseEntity<String> makeANewTransactionFromBankAccountToAppAccount(
            @RequestParam Double amount
    ) {
        try {
            System.out.println("has been called");
            bankTransactionService.makeANewTransactionFromBankAccountToAppAccount(amount);
            return ResponseEntity.ok().body("Transaction to app of " + amount + " made with success.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("An error occurend for transaction to app of " + amount + ". " + e);

        }
    }
}

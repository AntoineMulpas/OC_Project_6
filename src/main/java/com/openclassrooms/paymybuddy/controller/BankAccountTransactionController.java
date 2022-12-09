package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestBody BankTransaction bankTransaction
    ) {
        bankTransactionService.makeANewTransactionFromAppAccountToBankAccount(bankTransaction);
        return ResponseEntity.ok("Bank transaction has been made with success.");
    }

    @PostMapping("/to")
    public ResponseEntity<String> makeANewTransactionFromBankAccountToAppAccount(
            @RequestBody BankTransaction bankTransaction
    ) {
        bankTransactionService.makeANewTransactionFromBankAccountToAppAccount(bankTransaction);
        return ResponseEntity.ok("Bank transaction has been made with success.");
    }
}

package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank_account")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankTransactionService bankTransactionService;

    public BankAccountController(BankAccountService bankAccountService, BankTransactionService bankTransactionService) {
        this.bankAccountService = bankAccountService;
        this.bankTransactionService = bankTransactionService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBankAccountInformation(
            @RequestBody BankAccount bankAccount
            ) {
        try {
            String s = bankAccountService.addingBankAccountInformation(bankAccount);
            return ResponseEntity.ok(s);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.toString());
        }
    }




}

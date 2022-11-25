package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bank_account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/add")
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

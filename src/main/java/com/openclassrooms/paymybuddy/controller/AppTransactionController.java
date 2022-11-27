package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.service.AppTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
public class AppTransactionController {

    private final AppTransactionService appTransactionService;

    @Autowired
    public AppTransactionController(AppTransactionService appTransactionService) {
        this.appTransactionService = appTransactionService;
    }


    @PostMapping("/send")
    public AppTransaction makeANewAppTransaction(
            @RequestBody AppTransaction appTransaction
    ) {
        return appTransactionService.makeANewAppTransaction(appTransaction);
    }


}

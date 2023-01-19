package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.AppTransactionService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransferTLController {

    private final AppAccountService appAccountService;
    private final BankTransactionService bankTransactionService;
    private final AppTransactionService appTransactionService;

    @Autowired
    public TransferTLController(AppAccountService appAccountService, BankTransactionService bankTransactionService, AppTransactionService appTransactionService) {
        this.appAccountService = appAccountService;
        this.bankTransactionService = bankTransactionService;
        this.appTransactionService = appTransactionService;
    }

    @GetMapping("/transfer")
    public String getTransferPage(Model model) {
        model.addAttribute("pageName", "Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        model.addAttribute("transactionHistoric", bankTransactionService.getListOfBankTransactionForCurrentUser());
        model.addAttribute("appTransactionHistoric", appTransactionService.getListOfAppTransactionForCurrentUser());
        return "transfer";
    }
}

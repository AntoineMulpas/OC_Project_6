package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankAccountTLController {

    private final AppAccountService appAccountService;

    @Autowired
    public BankAccountTLController(AppAccountService appAccountService) {
        this.appAccountService = appAccountService;
    }

    @GetMapping("/bank-register")
    public String getBankRegisterPage(Model model) {
        model.addAttribute("pageName", "Bank Register");
        return "bank-register";
    }

    @GetMapping("/bank-transfer")
    public String getBankTransferPage(Model model) {
        model.addAttribute("pageName", "Bank Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "bank-transfer";
    }
}

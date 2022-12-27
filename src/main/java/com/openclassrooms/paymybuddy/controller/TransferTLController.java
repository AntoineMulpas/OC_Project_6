package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransferTLController {

    private final AppAccountService appAccountService;

    @Autowired
    public TransferTLController(AppAccountService appAccountService) {
        this.appAccountService = appAccountService;
    }

    @GetMapping("/transfer")
    public String getTransferPage(Model model) {
        model.addAttribute("pageName", "Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "transfer";
    }
}

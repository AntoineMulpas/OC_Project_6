package com.openclassrooms.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankAccountTLController {

    @GetMapping("/bank-register")
    public String getBankRegisterPage() {
        return "bank-register";
    }
}

package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;
    private final BankAccountService bankAccountService;

    @Autowired
    public HomeController(UserService userService, BankAccountService bankAccountService) {
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("pageName", "Home");
        model.addAttribute("userIsPresent", userService.isCurrentUserInformationSaved());
        model.addAttribute("bankAccountIsPresent", bankAccountService.bankAccountInformationArePresentOrNot());
        return "index";
    }

}

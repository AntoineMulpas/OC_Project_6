package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
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
    private final AppAccountService appAccountService;

    @Autowired
    public HomeController(UserService userService, BankAccountService bankAccountService, AppAccountService appAccountService) {
        this.userService = userService;
        this.bankAccountService = bankAccountService;
        this.appAccountService = appAccountService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("pageName", "Home");
        if (userService.isCurrentUserInformationSaved()) {
            model.addAttribute("userIsPresent", true);
        } else {
            model.addAttribute("userIsPresent", false);
        }
        if (bankAccountService.bankAccountInformationArePresentOrNot()) {
            model.addAttribute("bankAccountIsPresent", true);
        } else {
            model.addAttribute("bankAccountIsPresent", false);
        }
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        if (userService.isCurrentUserInformationSaved() && bankAccountService.bankAccountInformationArePresentOrNot()) {
            model.addAttribute("menuToDisplay", true);
        } else {
            model.addAttribute("menuToDisplay", false);
        }
        return "index";
    }

}

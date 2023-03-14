package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.CheckBox;
import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BankAccountTLController {

    private final AppAccountService appAccountService;
    private final BankTransactionService bankTransactionService;

    @Autowired
    public BankAccountTLController(AppAccountService appAccountService, BankTransactionService bankTransactionService) {
        this.appAccountService = appAccountService;
        this.bankTransactionService = bankTransactionService;
    }

    @GetMapping("/bank-register")
    public String getBankRegisterPage(Model model) {
        model.addAttribute("pageName", "Bank Register");
        return "bank-register";
    }

    @GetMapping("/bank-transfer")
    public String getBankTransferPage(Model model) {
        model.addAttribute("amountBankObj", new BankTransaction());
        model.addAttribute("checkbox", new CheckBox());
        model.addAttribute("pageName", "Bank Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "bank-transfer";
    }

    @PostMapping("/bank-transfer")
    public String AddMoneyToBankAccount(
            @ModelAttribute("amountBankObj")
            BankTransaction bankTransaction,
            @ModelAttribute("checkbox") CheckBox checkBox,
            Model model) {
        model.addAttribute("amountBank", new BankTransaction());
        model.addAttribute("checkbox", new CheckBox());
        Double amount = bankTransaction.getAmount();

        if (amount <= 0) {
            model.addAttribute("message", "Error, the amount should be superior to 0.");
        } else if (checkBox.isToBank() && checkBox.isToApp()) {
            model.addAttribute("message", "Error, choose only one element in the checkbox or at least one.");
        } else {
            try {
                if (checkBox.isToBank()) {
                    bankTransactionService.makeANewTransactionFromAppAccountToBankAccount(amount);
                    model.addAttribute("message", "You added + " + amount + "€ to your Bank Account.");
                } else {
                    bankTransactionService.makeANewTransactionFromBankAccountToAppAccount(amount);
                    model.addAttribute("message", "You added + " + amount + "€ to your App Account.");
                }
            } catch (Exception e) {
                model.addAttribute("message", e.getLocalizedMessage());
            }
        }

        model.addAttribute("pageName", "Bank Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "bank-transfer";
    }
}

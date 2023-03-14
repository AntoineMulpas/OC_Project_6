package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.AppTransactionService;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppTransactionTLController {

    private final UserRelationsService userRelationsService;
    private final AppAccountService appAccountService;
    private final AppTransactionService appTransactionService;

    @Autowired
    public AppTransactionTLController(UserRelationsService userRelationsService, AppAccountService appAccountService, AppTransactionService appTransactionService) {
        this.userRelationsService = userRelationsService;
        this.appAccountService = appAccountService;
        this.appTransactionService = appTransactionService;
    }

    @GetMapping("/app-transfer")
    public String getAppTransactionPage(Model model) {
        model.addAttribute("friendList", userRelationsService.getListOfUserRelations());
        model.addAttribute("pageName", "App Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "app-transfer";
    }

    @GetMapping("/app-transfer-friend/{id}")
    public String getAppTransactionPageForChosenFriend(Model model, @PathVariable Long id) {
        model.addAttribute("transfer", new AppTransaction(id));
        model.addAttribute("friendList", userRelationsService.getListOfUserRelations());
        model.addAttribute("pageName", "App Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "app-transfer-friend";
    }

    @PostMapping("/app-transfer-friend")
    public String getAppTransactionPageForChosenFriendPost(@ModelAttribute("transfer") AppTransaction appTransaction, Model model) {
        model.addAttribute("transfer", new AppTransaction());
        Double amount = appTransaction.getAmount();
        Long receiverId = appTransaction.getReceiverId();
        if (amount > 0 && receiverId != null) {
            try {
                appTransactionService.makeANewAppTransaction(receiverId, amount);
                model.addAttribute("message", "You send " + amount + "€ to your friend.");
            } catch (Exception e) {
                model.addAttribute("message", e.getLocalizedMessage());
            }
        } else {
            model.addAttribute("message", "The amount should be superior to 0.");
        }
        model.addAttribute("friendList", userRelationsService.getListOfUserRelations());
        model.addAttribute("pageName", "App Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "app-transfer";
    }

}

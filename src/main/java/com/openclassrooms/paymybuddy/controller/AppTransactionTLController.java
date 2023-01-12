package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppTransactionTLController {

    private final UserRelationsService userRelationsService;
    private final AppAccountService appAccountService;

    @Autowired
    public AppTransactionTLController(UserRelationsService userRelationsService, AppAccountService appAccountService) {
        this.userRelationsService = userRelationsService;
        this.appAccountService = appAccountService;
    }

    @GetMapping("/app-transfer")
    public String getAppTransactionPage(Model model) {
        model.addAttribute("friendList", userRelationsService.getListOfUserRelations());
        model.addAttribute("pageName", "App Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        return "app-transfer";
    }

    @GetMapping("/app-transfer-friend/{id}")
    public String getAppTransactionPageForChosenFriend(Model model, @PathVariable String id) {
        model.addAttribute("friendList", userRelationsService.getListOfUserRelations());
        model.addAttribute("pageName", "App Transfer");
        model.addAttribute("sold", appAccountService.getSoldOfAccount());
        model.addAttribute("friendId", id);
        return "app-transfer-friend";
    }

}

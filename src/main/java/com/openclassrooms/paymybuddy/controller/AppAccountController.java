package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.AppAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app_account")
public class AppAccountController {

    private final AppAccountService appAccountService;

    @Autowired
    public AppAccountController(AppAccountService appAccountService) {
        this.appAccountService = appAccountService;
    }

    @GetMapping("/sold")
    public Double getSoldOfAccount(
            @RequestParam Long id
    ) {
        return appAccountService.getSoldOfAccount(id);
    }

}

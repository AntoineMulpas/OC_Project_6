package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/add")
    public UserAuthentication saveANewUser(@RequestBody UserAuthentication userAuth) {
        try {
            return userAuthenticationService.saveAUser(userAuth);
        } catch (RuntimeException e) {
            return null;
        }
    }
}

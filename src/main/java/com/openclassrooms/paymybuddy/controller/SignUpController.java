package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public SignUpController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping("/signup")
    public String getSignUp(Model model) {
        model.addAttribute("userAuth", new UserAuthentication());
        return "signup";
    }

    @PostMapping("/signup")
    public String addNewUser(@ModelAttribute UserAuthentication userAuthentication, Model model) {
        System.out.println("Has been called.");
        model.addAttribute("userAuth", new UserAuthentication());
        try {
            userAuthenticationService.saveAUser(userAuthentication);
            model.addAttribute("message", "User added.");
        } catch (Exception e) {
            model.addAttribute("message", "Error while adding user.");
        }
        return "signup";
    }

}

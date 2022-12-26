package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserTLController {

    private final UserService userService;

    @Autowired
    public UserTLController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getUserProfilePage(Model model) {
        model.addAttribute("pageName", "Profile");
        model.addAttribute("information", userService.getCurrentUserInformation());
        return "profile";
    }


    @GetMapping("/register")
    public String saveUserInformation() {
        return "user";
    }


}

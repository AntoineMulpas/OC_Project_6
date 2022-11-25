package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.security.UserAuthenticationImpl;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private UserAuthenticationImpl userAuthentication;

    @Autowired
    public UserController(UserService userService, UserAuthenticationImpl userAuthentication) {
        this.userService = userService;
        this.userAuthentication = userAuthentication;
    }

    @GetMapping("/current")
    public String getCurrentUser() {
        return userAuthentication.getAuthentication().toString();
    }

    @PostMapping("/information")
    public User saveInformationOfAUser(
            @RequestBody User user
    ) {
        return userService.saveInformationOfAUser(user);
    }



}

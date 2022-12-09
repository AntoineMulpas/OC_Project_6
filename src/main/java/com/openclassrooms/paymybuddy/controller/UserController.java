package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public User saveInformationOfAUser(
            @RequestBody User user
    ) {
        return userService.saveInformationOfAUser(user);
    }

    @GetMapping("/information")
    public User getCurrentUserInformation() {
        return userService.getCurrentUserInformation();
    }
}

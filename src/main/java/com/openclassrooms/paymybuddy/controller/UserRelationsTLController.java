package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRelationsTLController {

    private final UserRelationsService userRelationsService;

    @Autowired
    public UserRelationsTLController(UserRelationsService userRelationsService) {
        this.userRelationsService = userRelationsService;
    }

    @GetMapping("/friends")
    public String getUserRelationsPage(Model model) {
        model.addAttribute("relations" ,userRelationsService.getListOfUserRelations());
        model.addAttribute("pageName", "Friends");
        return "friends";
    }

}

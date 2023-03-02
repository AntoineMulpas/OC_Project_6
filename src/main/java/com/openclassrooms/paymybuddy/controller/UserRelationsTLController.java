package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserRelationsTLController {

    private final UserRelationsService userRelationsService;

    @Autowired
    public UserRelationsTLController(UserRelationsService userRelationsService) {
        this.userRelationsService = userRelationsService;
    }

    @GetMapping("/friends")
    public String getUserRelationsPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("relations" ,userRelationsService.getListOfUserRelations());
        model.addAttribute("pageName", "Friends");
        model.addAttribute("userRelation", new UserRelations());
        return "friends";
    }

    @PostMapping("/friends")
    public String addNewUserRelation(@ModelAttribute User user, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRelation", new UserRelations());
        try {
            UserRelations userRelations = userRelationsService.addAFriend(user.getUserAuthentication());
            if (userRelations != null) {
                model.addAttribute("errorMessage", "User relation has been added.");
            } else {
                model.addAttribute("errorMessage", "User does not exist.");

            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while adding new user relation.");
        }
        model.addAttribute("relations" ,userRelationsService.getListOfUserRelations());
        return "friends";
    }

    @PostMapping("/friends/delete")
    public String deleteUserRelation(
            @ModelAttribute UserRelations userRelations,
            @RequestParam("id") String id,
            Model model) {
        model.addAttribute("userRelation", new UserRelations());
        model.addAttribute("user", new User());
        System.out.println("FRIEND ID = " + id);
            try {
                userRelationsService.deleteAFriend(Long.parseLong(id));
                model.addAttribute("deleteMessage", "Relation deleted.");
        } catch (Exception e) {
                model.addAttribute("deleteMessage", "An error occurred. Relation does not exist.");
            }
        model.addAttribute("relations" ,userRelationsService.getListOfUserRelations());
        return "friends";
    }

}

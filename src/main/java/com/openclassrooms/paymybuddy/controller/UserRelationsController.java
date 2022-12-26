package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relations")
public class UserRelationsController {

    private final UserRelationsService userRelationsService;

    public UserRelationsController(UserRelationsService userRelationsService) {
        this.userRelationsService = userRelationsService;
    }

    @GetMapping("/list")
    public List <UserRelationsDTO> getListOfUserRelations() {
        try {
            return userRelationsService.getListOfUserRelations();
        } catch (RuntimeException e) {
            return null;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAFriend(
          @RequestParam String email
    ) {
        try {
            userRelationsService.addAFriend(email);
            return ResponseEntity.status(200).body("Relation added successfully.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("User not found.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAFriend(
            @RequestParam Long friendId
    ) {
        try {
            userRelationsService.deleteAFriend(friendId);
            return ResponseEntity.ok().body("Friend with id: " + friendId + " has been deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("An error occurred while deleting friend.");
        }
    }


}

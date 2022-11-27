package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List <UserRelations> getListOfUserRelations() {
        try {
            return userRelationsService.getListOfUserRelations();
        } catch (RuntimeException e) {
            return null;
        }
    }

    @PostMapping("/add")
    public UserRelations addAFriend(
          @RequestBody UserRelations userRelations
    ) {
        try {
            return userRelationsService.addAFriend(userRelations.getFriendId());
        } catch (RuntimeException e) {
            return null;
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAFriend(
            @RequestBody UserRelations userRelations
    ) {
        try {
            userRelationsService.deleteAFriend(userRelations.getUserId(), userRelations.getFriendId());
            return ResponseEntity.ok().body("Friend with id: " + userRelations.getFriendId() + " has been deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("An error occurred while deleting friend.");
        }
    }


}

package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.service.UserRelationsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relations")
public class UserRelationsController {

    private final UserRelationsService userRelationsService;

    private static final Logger logger = LogManager.getLogger(UserRelationsController.class);


    public UserRelationsController(UserRelationsService userRelationsService) {
        this.userRelationsService = userRelationsService;
    }

    @GetMapping("/list")
    public ResponseEntity<List <UserRelationsDTO>> getListOfUserRelations() {
        try {
            List <UserRelationsDTO> listOfUserRelations = userRelationsService.getListOfUserRelations();
            logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " fetched the list of his relation.");
            return ResponseEntity.ok().body(listOfUserRelations);
        } catch (RuntimeException e) {
            logger.error("An error occurred while getting list of user relations for user: " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAFriend(
          @RequestParam String email
    ) {
        try {
            userRelationsService.addAFriend(email);
            return ResponseEntity.status(200).body("Relation added successfully for user " + SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (IllegalArgumentException e) {
            logger.error("An error occurred while adding a new friend for current user: " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("User not found.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAFriend(
            @RequestParam Long friendId
    ) {
        try {
            userRelationsService.deleteAFriend(friendId);
            return ResponseEntity.ok().body("Friend with id: " + friendId + " has been deleted for user " + SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (RuntimeException e) {
            logger.error("An error occurred while deleting friend for current user: " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("An error occurred while deleting friend.");
        }
    }


}

package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserDTO;
import com.openclassrooms.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> saveInformationOfAUser(
            @RequestBody User user
    ) {
        try {
            User user1 = userService.saveInformationOfAUser(user);
            return new ResponseEntity <>(user1, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("An error occurred while saving information regarding new user: " + user.getUserAuthentication() + ". "  +e);
            return new ResponseEntity <>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/information")
    public ResponseEntity<UserDTO> getCurrentUserInformation() {
        try {
            UserDTO currentUserInformation = userService.getCurrentUserInformation();
            return new ResponseEntity<>(currentUserInformation, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("An error occurred while fetching current user information for user: " + SecurityContextHolder.getContext().getAuthentication().getName() + ". "  + e);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/information-saved")
    public ResponseEntity<Boolean> isCurrentUserInformationSaved() {
        try {
            Boolean currentUserInformationSaved = userService.isCurrentUserInformationSaved();
            return new ResponseEntity <>(currentUserInformationSaved, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("An error occurred while finding if current user information are already saved: " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
}

package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.service.UserAuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/v1/authentication")
public class UserAuthenticationController {

    /*

    private final UserAuthenticationService userAuthenticationService;

    private static final Logger logger = LogManager.getLogger(UserAuthenticationController.class);


    @Autowired
    public UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserAuthentication> saveANewUser(@RequestBody UserAuthentication userAuth) {
        try {
            UserAuthentication userAuthentication = userAuthenticationService.saveAUser(userAuth);
            logger.info("New user has been saved: " + userAuthentication.getUsername());
            return ResponseEntity.ok().body(userAuthentication);
        } catch (RuntimeException e) {
            logger.error("An error occurred while saving new user. " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }


     */
}

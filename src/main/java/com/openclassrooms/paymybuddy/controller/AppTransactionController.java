package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.service.AppTransactionService;
import com.openclassrooms.paymybuddy.service.IdOfUserAuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class AppTransactionController {

    private final AppTransactionService appTransactionService;
private static final Logger logger = LogManager.getLogger(AppTransactionController.class);


    @Autowired
    public AppTransactionController(AppTransactionService appTransactionService) {
        this.appTransactionService = appTransactionService;
    }


    @PostMapping("/send")
    public ResponseEntity<AppTransaction> makeANewAppTransaction(
            @RequestParam String receiverId,
            @RequestParam Double amount
    ) {
        try {
            Long idToPass = Long.parseLong(receiverId);
            AppTransaction appTransaction = appTransactionService.makeANewAppTransaction(idToPass, amount);
            logger.info("User: " + SecurityContextHolder.getContext().getAuthentication().getName() + " has requested a new AppTransaction to " + receiverId);
            return ResponseEntity.ok().body(appTransaction);
        } catch (RuntimeException e) {
            logger.error("An error occurred while making a new app transaction for user:  " + SecurityContextHolder.getContext().getAuthentication().getName() + ". " + e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }


}

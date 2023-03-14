package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdOfUserAuthenticationService {

    private final UserAuthRepository userAuthRepository;
    private static final Logger logger = LogManager.getLogger(IdOfUserAuthenticationService.class);


    @Autowired
    public IdOfUserAuthenticationService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    public Long getUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <UserAuthentication> userAuthentication = userAuthRepository.findByUsernameEquals(username);
        if (userAuthentication.isPresent()) {
            return userAuthentication.get().getId();
        } else {
            logger.error("User account does not exist for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new RuntimeException("User does not exist.");
        }
    }

    public Boolean userIdExists(Long userId) {
        return userAuthRepository.existsById(userId);
    }

}

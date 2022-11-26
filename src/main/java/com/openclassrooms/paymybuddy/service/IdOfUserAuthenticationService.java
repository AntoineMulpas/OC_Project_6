package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdOfUserAuthenticationService {

    private final UserAuthRepository userAuthRepository;

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
            throw new RuntimeException("User does not exist.");
        }
    }

}

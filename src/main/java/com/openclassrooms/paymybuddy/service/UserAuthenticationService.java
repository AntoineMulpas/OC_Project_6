package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserAuthenticationService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppAccountService appAccountService;

    private static final Logger logger = LogManager.getLogger(UserAuthenticationService.class);

    @Autowired
    public UserAuthenticationService(UserAuthRepository userAuthRepository, PasswordEncoder passwordEncoder, AppAccountService appAccountService) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.appAccountService = appAccountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthRepository
                .findByUsernameEquals(username)
                .orElseThrow(
                        () -> {
                            logger.error("Cannot load user details, " + username + " does not exist.");
                            return new UsernameNotFoundException(
                                    "Username: " + username + " not found.");
                        });
    }


    public UserAuthentication saveAUser(UserAuthentication user) {
        validateUser(user);
        if (userAuthRepository.findByUsernameEquals(user.getUsername()).isPresent()) {
            logger.error("Username already exists for " + user);
            throw new RuntimeException("Username already used.");
        }

        UserAuthentication userToSave = new UserAuthentication(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword())
        );
        UserAuthentication savedUser = userAuthRepository.save(userToSave);
        appAccountService.createAppAccount(savedUser.getId());
        logger.info("User: " + user.getUsername() + " has been saved successfully.");
        return userToSave;
    }

    private void validateUser(UserAuthentication user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            logger.error("Username cannot been empty when saving a user.");
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            logger.error("Password cannot been empty when saving a user.");
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }


    public UserAuthentication findIdOfUserByUsername(String username) {
        Optional<UserAuthentication> optionalUserAuthentication = userAuthRepository.findByUsernameEquals(username);
        if (optionalUserAuthentication.isPresent()) {
            return optionalUserAuthentication.get();
        } else {
            logger.error("Username not found for username: " + username);
            throw new UsernameNotFoundException("Username not found.");
        }
    }

    public String findUsernameById(Long friendId) {
        return userAuthRepository.findById(friendId)
                .map(UserAuthentication::getUsername)
                .orElseThrow(() -> {
                    logger.error("User not found for id " + friendId);
                    return new IllegalArgumentException("User not found with id: " + friendId);
                });
    }

}

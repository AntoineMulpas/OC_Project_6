package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserAuthenticationService(UserAuthRepository userAuthRepository, PasswordEncoder passwordEncoder) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthRepository
                .findByUsernameEquals(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Username: " + username + " not found."));
    }


    public UserAuthentication saveAUser(UserAuthentication user) {
            if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty()) {
                if (userAuthRepository.findByUsernameEquals(user.getUsername()).isEmpty()) {
                    UserAuthentication userToSave = new UserAuthentication(
                            user.getUsername(),
                            passwordEncoder.encode(user.getPassword())
                            );
                    userAuthRepository.save(userToSave);
                    return userToSave;
                } else {
                    throw new RuntimeException("Username already used.");
                }
            } else {
                throw new NullPointerException();
            }
    }

    public Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}

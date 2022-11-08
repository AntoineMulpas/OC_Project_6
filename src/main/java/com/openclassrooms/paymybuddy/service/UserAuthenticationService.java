package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserAuth;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    private PasswordEncoder passwordEncoder;
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


    public UserAuth saveAUser(UserAuth userAuth) {
            if (userAuth.getUsername() != null && userAuth.getPassword() != null) {
                if (userAuthRepository.findByUsernameEquals(userAuth.getUsername()).isEmpty()) {
                    UserAuth userToSave = new UserAuth(
                            userAuth.getUsername(),
                            passwordEncoder.encode(userAuth.getPassword()));
                    userAuthRepository.save(userToSave);
                    return userToSave;
                } else {
                    throw new RuntimeException("Username already used.");
                }
            } else {
                throw new NullPointerException();
            }
    }
}

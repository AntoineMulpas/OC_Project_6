package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserDTO;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User saveInformationOfAUser(User user) {
        Optional <User> userAlreadyExistsOrNot = userRepository.findUserByFirstNameEqualsAndLastNameEqualsAndBirthdayEquals(
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday()
        );
        if (userAlreadyExistsOrNot.isEmpty()) {
            String userAuth = getCurrentUser();
            User userToSave = new User(
                    user.getFirstName(),
                    user.getLastName(),
                    userAuth,
                    user.getBirthday()
            );
            userRepository.save(userToSave);
            return userToSave;
        } else {
            throw new RuntimeException("User already exists.");
        }
    }

    public UserDTO getCurrentUserInformation() {
        String currentUser = getCurrentUser();
        Optional <User> userByUserAuthenticationEquals = userRepository.findUserByUserAuthenticationEquals(currentUser);
        if (userByUserAuthenticationEquals.isPresent()) {
            return new UserDTO(
                    userByUserAuthenticationEquals.get().getFirstName(),
                    userByUserAuthenticationEquals.get().getLastName(),
                    userByUserAuthenticationEquals.get().getBirthday(),
                    currentUser);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    public User getUserInformation(Long id) {
        Optional <User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with id: " + id + " not found.");
        }
    }
}

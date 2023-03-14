package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.model.UserDTO;
import com.openclassrooms.paymybuddy.repository.UserAuthRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class);


    public UserService(UserRepository userRepository, UserAuthRepository userAuthRepository) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
    }

    protected String getCurrentUser() {
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
            logger.info(SecurityContextHolder.getContext().getAuthentication().getName() + " has successfully saved his information.");
            return userToSave;
        } else {
            logger.error("User information are already present for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new RuntimeException("User already exists.");
        }
    }

    public UserDTO getCurrentUserInformation() {
        String currentUser = getCurrentUser();
        Optional <User> userByUserAuthenticationEquals = userRepository.findUserByUserAuthenticationEquals(currentUser);
        logger.info(currentUser + " has fetched his information.");
        return userByUserAuthenticationEquals.map(user -> new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday(),
                currentUser)).orElseGet(UserDTO::new);
    }

    public Boolean isCurrentUserInformationSaved() {
        String currentUser = getCurrentUser();
        Optional <User> userByUserAuthenticationEquals = userRepository.findUserByUserAuthenticationEquals(currentUser);
        return userByUserAuthenticationEquals.isPresent();
    }

    public User getUserInformation(Long id) {
        Optional <UserAuthentication> userAuthRepositoryById = userAuthRepository.findById(id);
        if (userAuthRepositoryById.isPresent()) {
            String userAuthentication = userAuthRepositoryById.get().getUsername();
            Optional <User> user = userRepository.findUserByUserAuthenticationEquals(userAuthentication);
            if (user.isPresent()) {
                return user.get();
            } else {
                logger.error("User with id: " + id + " not found.");
                throw new UsernameNotFoundException("User with id: " + id + " not found.");
            }
        }
        throw new RuntimeException("User not found.");
    }

    public User getUserInformationByUsername(String friendUsername) {
        Optional <User> userByUserAuthenticationEquals = userRepository.findUserByUserAuthenticationEquals(friendUsername);
        return userByUserAuthenticationEquals.orElse(null);
    }
}

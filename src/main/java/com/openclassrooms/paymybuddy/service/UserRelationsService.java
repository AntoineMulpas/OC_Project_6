package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRelationsService {

    private final UserRelationsRepository userRelationsRepository;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;
    private final UserAuthenticationService userAuthenticationService;
    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(UserRelationsService.class);


    public UserRelationsService(UserRelationsRepository userRelationsRepository, IdOfUserAuthenticationService idOfUserAuthenticationService, UserAuthenticationService userAuthenticationService, UserService userService) {
        this.userRelationsRepository = userRelationsRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
    }

    public List <UserRelationsDTO> getListOfUserRelations() {
        Long userId = idOfUserAuthenticationService.getUserId();
        List <UserRelations> listOfRelations = userRelationsRepository.findAllByUserIdEquals(userId);
        List<UserRelationsDTO> listToReturn = new ArrayList <>();
        listOfRelations.forEach(userRelations -> {
            Long friendId = userRelations.getFriendId();
            String friendUsername = userAuthenticationService.findUsernameById(friendId);
            User userInformation = userService.getUserInformationByUsername(friendUsername);
            listToReturn.add(new UserRelationsDTO(friendId,userInformation.getFirstName(), userInformation.getLastName()));
        });
        logger.info("List of user relation has been fetched for user: " + userId);
        return listToReturn;
    }

    public UserRelations addAFriend (String email) {
        Long userId = idOfUserAuthenticationService.getUserId();
        Long friendId = userAuthenticationService.findIdOfUserByUsername(email).getId();

        Optional <UserRelations> userRelations = userRelationsRepository.findUserRelationsByFriendIdEqualsAndUserIdEquals(friendId, userId);
        if (userRelations.isPresent()) {
            logger.error("Relation already exist for " + SecurityContextHolder.getContext().getAuthentication().getName() + " and " + email);
            throw new IllegalArgumentException("Relation already exist");
        }
        UserRelations newUserRelations = new UserRelations(userId, friendId);
        userRelationsRepository.save(newUserRelations);
        logger.info("User " + userId + " has been successfully added new relation with id " + friendId);
        return newUserRelations;
    }

    public boolean deleteAFriend(Long friendId) {
        Long userId = idOfUserAuthenticationService.getUserId();
        UserRelations relations = userRelationsRepository
                .findUserRelationsByUserIdEqualsAndFriendIdEquals(userId, friendId)
                .orElseThrow(() -> {
                    logger.error("Relation is not present for " + userId + " and " + friendId);
                    return new RuntimeException("Relation is not present.");
                });
        userRelationsRepository.delete(relations);
        logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has been successfully deleted relation with id " + friendId);
        return true;
    }


}

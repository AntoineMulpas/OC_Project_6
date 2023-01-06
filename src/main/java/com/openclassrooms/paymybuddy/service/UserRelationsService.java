package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
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
            System.out.println(userRelations);
            Long friendId = userRelations.getFriendId();
            String friendUsername = userAuthenticationService.findUsernameById(friendId);
            User userInformation = userService.getUserInformationByUsername(friendUsername);
            listToReturn.add(new UserRelationsDTO(friendId,userInformation.getFirstName(), userInformation.getLastName()));
        });
        return listToReturn;
    }

    public UserRelations addAFriend (String email) {
            Long userId = idOfUserAuthenticationService.getUserId();
            Long friendId = userAuthenticationService.findIdOfUserByUsername(email).getId();
            Optional <UserRelations> userRelationsByFriendIdEquals = userRelationsRepository.findUserRelationsByFriendIdEqualsAndUserIdEquals(friendId, userId);
            if (userRelationsByFriendIdEquals.isEmpty()) {
                UserRelations userRelations = new UserRelations(userId, friendId);
                userRelationsRepository.save(userRelations);
                return userRelations;
            } else {
                throw new RuntimeException("Relation already exists.");
            }
    }

    public void deleteAFriend(Long friendId) {
        try {
            Long userId = idOfUserAuthenticationService.getUserId();
            UserRelations relations = userRelationsRepository.findUserRelationsByUserIdEqualsAndFriendIdEquals(userId, friendId);
            if (relations != null) {
                userRelationsRepository.delete(relations);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

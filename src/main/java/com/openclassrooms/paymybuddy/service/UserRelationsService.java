package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRelationsService {

    private final UserRelationsRepository userRelationsRepository;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;

    public UserRelationsService(UserRelationsRepository userRelationsRepository, IdOfUserAuthenticationService idOfUserAuthenticationService) {
        this.userRelationsRepository = userRelationsRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
    }

    public List <UserRelations> getListOfUserRelations() {
        return userRelationsRepository.findAllByUserIdEquals(1L);
    }

    public UserRelations addAFriend (Long friendId) {
        try {
            Long userId = idOfUserAuthenticationService.getUserId();
            UserRelations userRelations = new UserRelations(userId, friendId);
            userRelationsRepository.save(userRelations);
            return userRelations;
        } catch (Exception exception) {
            return null;
        }
    }

    public void deleteAFriend(Long userId, Long friendId) {
        try {
            UserRelations relations = userRelationsRepository.findUserRelationsByUserIdEqualsAndFriendIdEquals(userId, friendId);
            if (relations != null) {
                userRelationsRepository.delete(relations);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

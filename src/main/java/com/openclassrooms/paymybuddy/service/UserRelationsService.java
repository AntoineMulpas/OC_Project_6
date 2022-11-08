package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRelationsService {

    private final UserRelationsRepository userRelationsRepository;

    public UserRelationsService(UserRelationsRepository userRelationsRepository) {
        this.userRelationsRepository = userRelationsRepository;
    }

    public List <UserRelations> getListOfUserRelations(Long id) {
        return userRelationsRepository.findAllByUserIdEquals(id);
    }

    public UserRelations addAFriend (Long userId, Long friendId) {
        try {
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

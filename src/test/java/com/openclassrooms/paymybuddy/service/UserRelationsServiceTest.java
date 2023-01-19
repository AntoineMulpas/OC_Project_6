package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRelationsServiceTest {

    private UserRelationsService underTest;
    @Mock
    private UserRelationsRepository userRelationsRepository;
    @Mock
    private IdOfUserAuthenticationService idOfUserAuthenticationService;
    @Mock
    private UserService userService;
    @Mock
    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    void setUp() {
        underTest = new UserRelationsService(userRelationsRepository, idOfUserAuthenticationService, userAuthenticationService, userService);
    }

    @Test
    void getListOfUserRelations() {
        List <UserRelationsDTO> listOfUserRelations = underTest.getListOfUserRelations();
        assertEquals(0 ,listOfUserRelations.size());
    }

    @Test
    void addAFriend() {
        UserRelations addAFriend = underTest.addAFriend("mustang");
        UserRelations toCompare = new UserRelations(1L, 1L, 2L);
        assertEquals(addAFriend.getFriendId(), toCompare.getFriendId());

    }

    @Test
    void deleteAFriend() {
        UserRelations userRelations = new UserRelations(1L, 2L);
        underTest.deleteAFriend(1L, 2L);
        verify(userRelationsRepository).findUserRelationsByUserIdEqualsAndFriendIdEquals(1L, 2L);
    }
}
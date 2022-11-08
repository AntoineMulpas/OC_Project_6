package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRelationsServiceTest {

    private UserRelationsService underTest;
    @Mock
    private UserRelationsRepository userRelationsRepository;

    @BeforeEach
    void setUp() {
        underTest = new UserRelationsService(userRelationsRepository);
    }

    @Test
    void getListOfUserRelations() {
        List <UserRelations> listOfUserRelations = underTest.getListOfUserRelations(1L);
        assertEquals(0 ,listOfUserRelations.size());
    }

    @Test
    void addAFriend() {
        UserRelations addAFriend = underTest.addAFriend(1L, 2L);
        UserRelations toCompare = new UserRelations(1L, 1L, 2L);
        assertEquals(addAFriend.getUserId(), toCompare.getUserId());

    }

    @Test
    void deleteAFriend() {
        UserRelations userRelations = new UserRelations(1L, 2L);
        underTest.deleteAFriend(1L, 2L);
        verify(userRelationsRepository).findUserRelationsByUserIdEqualsAndFriendIdEquals(1L, 2L);
    }
}
package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserAuthentication;
import com.openclassrooms.paymybuddy.model.UserRelations;
import com.openclassrooms.paymybuddy.model.UserRelationsDTO;
import com.openclassrooms.paymybuddy.repository.UserRelationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        List<UserRelations> mockedList = List.of(new UserRelations(1L, 2L));
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(userRelationsRepository.findAllByUserIdEquals(1L)).thenReturn(mockedList);
        when(userAuthenticationService.findUsernameById(2L)).thenReturn("antoine");
        when(userService.getUserInformationByUsername("antoine")).thenReturn(new User("antoine", "antoine", "antoine", LocalDate.now()));
        List <UserRelationsDTO> listOfUserRelations = underTest.getListOfUserRelations();
        assertEquals(1 ,listOfUserRelations.size());
    }

    @Test
    void addAFriend() {
        when(userAuthenticationService.findIdOfUserByUsername("mustang")).thenReturn(new UserAuthentication(2L, "mustang", "password"));
        UserRelations addAFriend = underTest.addAFriend("mustang");
        UserRelations toCompare = new UserRelations(1L, 1L, 2L);
        assertEquals(addAFriend.getFriendId(), toCompare.getFriendId());
    }

    @Test
    void addAFriendShouldThrow() {
        Optional<UserRelations> userRelations = Optional.of(new UserRelations(1L));
        when(userAuthenticationService.findIdOfUserByUsername("mustang")).thenReturn(new UserAuthentication(2L, "mustang", "password"));
        when(userRelationsRepository.findUserRelationsByFriendIdEqualsAndUserIdEquals(any(), any())).thenReturn(userRelations);
        assertThrows(RuntimeException.class, () -> underTest.addAFriend("antoine"));
    }

    @Test
    void deleteAFriend() {
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(userRelationsRepository.findUserRelationsByUserIdEqualsAndFriendIdEquals(1L, 2L)).thenReturn(Optional.of(new UserRelations(1L, 1L, 2L)));
        underTest.deleteAFriend(2L);
        verify(userRelationsRepository).findUserRelationsByUserIdEqualsAndFriendIdEquals(1L, 2L);
    }

    @Test
    void deleteAFriendShouldThrow() {
        Optional<UserRelations> optionalUserRelations = Optional.empty();
        when(idOfUserAuthenticationService.getUserId()).thenReturn(1L);
        when(userRelationsRepository.findUserRelationsByUserIdEqualsAndFriendIdEquals(1L, 2L)).thenReturn(optionalUserRelations);
        assertThrows(RuntimeException.class, () -> underTest.deleteAFriend(1L));
    }
}
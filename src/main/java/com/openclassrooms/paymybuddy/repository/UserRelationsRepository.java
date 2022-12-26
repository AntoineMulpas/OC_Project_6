package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.UserRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRelationsRepository extends JpaRepository<UserRelations, Long> {

    List <UserRelations> findAllByUserIdEquals(Long id);

    UserRelations findUserRelationsByUserIdEqualsAndFriendIdEquals(Long userId, Long friendId);

    Optional<UserRelations> findUserRelationsByFriendIdEqualsAndUserIdEquals(Long friendId, Long id);



}

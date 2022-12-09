package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findUserByFirstNameEqualsAndLastNameEqualsAndBirthdayEquals(String firstName, String lastName, LocalDate birthday);
     Optional<User> findUserByUserAuthenticationEquals(String username);

}

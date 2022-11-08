package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUsernameEquals(String username);

}

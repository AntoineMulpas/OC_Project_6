package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.AppAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAccountRepository extends JpaRepository<AppAccount, Long> {

}

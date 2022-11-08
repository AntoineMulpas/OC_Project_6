package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTransactionRepository extends JpaRepository<AppTransaction, Long> {
}

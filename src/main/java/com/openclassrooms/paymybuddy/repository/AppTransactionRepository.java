package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppTransactionRepository extends JpaRepository<AppTransaction, Long> {

    @Query(value = "select * from app_transaction as a where a.receiver_id = ?1 or a.sender_id = ?1", nativeQuery = true)
    List <AppTransaction> findByIdOfCurrentUser(Long id);
}

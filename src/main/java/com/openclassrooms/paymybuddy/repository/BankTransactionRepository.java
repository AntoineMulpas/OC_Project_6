package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {



}

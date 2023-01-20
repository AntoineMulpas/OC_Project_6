package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<Fees, Long> {
}

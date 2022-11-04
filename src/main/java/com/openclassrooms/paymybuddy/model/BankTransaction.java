package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "BankTransaction")
@Table(name = "bank_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long userId;
    private LocalDateTime date;
    private Double amount;
    private Character operation;

}

package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Fees")
@Table(name = "fees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Fees {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long bankTransactionId;

    private Long appTransactionId;

    private LocalDateTime date;

    private Double amount;


    public Fees(Long bankTransactionId, Long appTransactionId, LocalDateTime date, Double amount) {
        this.bankTransactionId = bankTransactionId;
        this.appTransactionId = appTransactionId;
        this.date = date;
        this.amount = amount;
    }
}

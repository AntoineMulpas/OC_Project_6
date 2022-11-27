package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "AppTransaction")
@Table(name = "app_transaction")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AppTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long senderId;
    private Long receiverId;
    private LocalDateTime localDateTime;
    private Double amount;

    public AppTransaction(Long receiverId, Double amount) {
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public AppTransaction(Long senderId, Long receiverId, LocalDateTime localDateTime, Double amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.localDateTime = localDateTime;
        this.amount = amount;
    }
}

package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "AppTransaction")
@Table(name = "app_transaction")
@AllArgsConstructor
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

}

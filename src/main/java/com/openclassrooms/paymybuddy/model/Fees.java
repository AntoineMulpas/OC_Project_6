package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;

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

    private Long transactionId;



}

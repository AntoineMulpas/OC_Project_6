package com.openclassrooms.paymybuddy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "BankAccount")
@Table(name = "bank_account")
@Getter
@Setter
@EqualsAndHashCode
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String accountNumber;
    private String iban;
    private String swift;
    private Integer bankCode;
    private Integer counterCode;
    private Integer ribKey;
    private Long userId;

}

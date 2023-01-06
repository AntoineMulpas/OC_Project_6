package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "BankAccount")
@Table(name = "bank_account")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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
    private String username;

    public BankAccount(String accountNumber, String iban, String swift, Integer bankCode, Integer counterCode, Integer ribKey) {
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.swift = swift;
        this.bankCode = bankCode;
        this.counterCode = counterCode;
        this.ribKey = ribKey;
    }

    public BankAccount(String accountNumber, String iban, String swift, Integer bankCode, Integer counterCode, Integer ribKey, String username) {
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.swift = swift;
        this.bankCode = bankCode;
        this.counterCode = counterCode;
        this.ribKey = ribKey;
        this.username = username;
    }

    public BankAccount(String iban, String swift) {
        this.iban = iban;
        this.swift = swift;
    }
}

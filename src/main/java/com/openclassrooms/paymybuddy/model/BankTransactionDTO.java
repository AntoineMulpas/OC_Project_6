package com.openclassrooms.paymybuddy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankTransactionDTO {

    private String date;
    private String sender;
    private String receiver;
    private Double amount;

}

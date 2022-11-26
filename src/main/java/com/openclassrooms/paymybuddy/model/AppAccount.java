package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "AppAccount")
@Table(name = "app_account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AppAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double sold;
    private Long userId;

    public AppAccount(Double sold, Long userId) {
        this.sold = sold;
        this.userId = userId;
    }
}

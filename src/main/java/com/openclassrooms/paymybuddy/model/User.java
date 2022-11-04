package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;


}

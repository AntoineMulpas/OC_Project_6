package com.openclassrooms.paymybuddy.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;


}

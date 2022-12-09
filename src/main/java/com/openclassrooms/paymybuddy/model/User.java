package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String userAuthentication;
    private LocalDate birthday;

    public User(String firstName, String lastName, String userAuthentication, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userAuthentication = userAuthentication;
        this.birthday = birthday;
    }

    public User(String firstName, String lastName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }
}

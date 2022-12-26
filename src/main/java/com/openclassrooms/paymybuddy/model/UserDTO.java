package com.openclassrooms.paymybuddy.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;

}

package com.openclassrooms.paymybuddy.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "UserRelations")
@Table(name = "user_relations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserRelations {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long userId;
    private Long friendId;

}

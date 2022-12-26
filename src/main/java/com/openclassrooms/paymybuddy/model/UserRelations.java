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
@ToString
public class UserRelations {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long userId;
    private Long friendId;

    public UserRelations(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public UserRelations(Long friendId) {
        this.friendId = friendId;
    }
}

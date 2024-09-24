package com.forj.auth.domain.model;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(UserEntityListener.class)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private String slackId;

    public static User signup(String username, String password, UserRole role, String slackId) {

        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .slackId(slackId)
                .build();

    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }

}

package com.forj.auth.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class User {

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

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "slack_id")
//    private Slack slack;

    public static User signup(String username, String password, UserRole role) {

        return User.builder()
                .username(username)
                .password(password)
                .role(role)
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

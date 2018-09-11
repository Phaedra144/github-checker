package com.greenfox.szilvi.githubchecker.user.persistance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {

    @Id
    long id;
    String login;
    String accessToken;
    @OneToOne
    @JoinColumn(name = "auth_id")
    Auth auth;

    public User(String accessToken) {
        this.accessToken = accessToken;
    }

    public User() {
    }
}

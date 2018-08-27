package com.greenfox.szilvi.githubchecker.user.persistance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {

    @Id
    long id;
    String login;
    String accessToken;

    public User(String accessToken) {
        this.accessToken = accessToken;
    }

    public User() {
    }
}

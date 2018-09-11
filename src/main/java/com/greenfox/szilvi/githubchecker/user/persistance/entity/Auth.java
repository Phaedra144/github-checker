package com.greenfox.szilvi.githubchecker.user.persistance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String accessToken;

    public Auth() {
    }

    public Auth(String accessToken) {
        this.accessToken = accessToken;
    }
}

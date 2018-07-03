package com.greenfox.szilvi.githubchecker.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    long id;
    String login;
    String accessToken;
}

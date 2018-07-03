package com.greenfox.szilvi.githubchecker.greenfoxteam.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class GreenfoxTeamStatus {

    String httpStatus;
    String message;
    String state;
    String ghHandle;

    public GreenfoxTeamStatus() {
    }

    public GreenfoxTeamStatus(String httpStatus, String ghHandle) {
        this.httpStatus = httpStatus;
        this.ghHandle = ghHandle;
    }

    @Override
    public String toString() {
        return "GreenfoxTeamStatus{" +
                "httpStatus='" + httpStatus + '\'' +
                ", state='" + state + '\'' +
                ", ghHandle='" + ghHandle + '\'' +
                '}';
    }
}

package com.greenfox.szilvi.githubchecker.greenfoxteam.web.dto;

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
    String actionType;

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
                ", message='" + message + '\'' +
                ", state='" + state + '\'' +
                ", ghHandle='" + ghHandle + '\'' +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}

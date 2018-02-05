package com.greenfox.szilvi.githubchecker.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    String errorType;
    String message;
}

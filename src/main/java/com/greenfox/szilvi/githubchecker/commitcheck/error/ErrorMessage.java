package com.greenfox.szilvi.githubchecker.commitcheck.error;

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

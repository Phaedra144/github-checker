package com.greenfox.szilvi.githubchecker.commitcheck.model;

import lombok.Getter;

/**
 * Created by Szilvi on 2017. 10. 04..
 */

public class Commit {

    @Getter
    private Author author;
    private String message;

    @Override
    public String toString() {
        return "Commit{" +
                "author=" + author +
                ", message='" + message + '\'' +
                '}';
    }
}

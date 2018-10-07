package com.greenfox.szilvi.githubchecker.commitcheck.web.dto;

import lombok.Getter;

public class Author {

    private String name;
    @Getter
    private String date;

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

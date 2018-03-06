package com.greenfox.szilvi.githubchecker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForkedRepo {

    private int id;
    private String fullName;
    private String language;

    public ForkedRepo() {
    }

    public ForkedRepo(int id, String fullName, String language) {
        this.id = id;
        this.fullName = fullName;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

package com.greenfox.szilvi.githubchecker.commitcheck.web.dto;

public class ForkedRepo {

    private int id;
    private String full_name;
    private String language;

    public ForkedRepo() {
    }

    public ForkedRepo(int id, String fullName, String language) {
        this.id = id;
        this.full_name = fullName;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

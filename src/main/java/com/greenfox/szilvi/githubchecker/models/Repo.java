package com.greenfox.szilvi.githubchecker.models;

import lombok.Getter;

import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
public class Repo {

    private int id;
    @Getter
    private String name;

    public Repo(int id, String name, List<String> topics) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
package com.greenfox.szilvi.githubchecker.models;

import lombok.Getter;

/**
 * Created by Szilvi on 2017. 09. 30..
 */
public class GfCommits {

    private String sha;
    @Getter
    private Commit commit;

    @Override
    public String toString() {
        return "GfCommits{" +
                "sha='" + sha + '\'' +
                ", commit=" + commit +
                '}';
    }
}

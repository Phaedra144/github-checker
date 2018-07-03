package com.greenfox.szilvi.githubchecker.commitcheck.model;

import lombok.Getter;

@Getter
public class GfCommits {

    private String sha;
    private Commit commit;

    @Override
    public String toString() {
        return "GfCommits{" +
                "sha='" + sha + '\'' +
                ", commit=" + commit +
                '}';
    }
}

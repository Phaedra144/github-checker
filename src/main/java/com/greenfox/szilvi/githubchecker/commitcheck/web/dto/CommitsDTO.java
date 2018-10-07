package com.greenfox.szilvi.githubchecker.commitcheck.web.dto;

import lombok.Getter;

@Getter
public class CommitsDTO {
    private String sha;
    private Commit commit;

}

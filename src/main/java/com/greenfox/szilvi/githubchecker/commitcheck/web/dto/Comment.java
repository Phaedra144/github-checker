package com.greenfox.szilvi.githubchecker.commitcheck.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    long id;
    @JsonProperty(value = "commit_id")
    String commitId;
    @JsonProperty(value = "created_at")
    String createdAt;

}

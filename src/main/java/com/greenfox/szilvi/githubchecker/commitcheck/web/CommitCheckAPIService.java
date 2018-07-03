package com.greenfox.szilvi.githubchecker.commitcheck.web;

import com.greenfox.szilvi.githubchecker.retrofit.GitHubRetrofit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommitCheckAPIService {

    @Autowired
    GitHubRetrofit gitHubRetrofit;

    CommitCheckAPI commitCheckAPI;

    public CommitCheckAPI getCommitCheckAPI() {
        commitCheckAPI = gitHubRetrofit.getRetrofit().create(CommitCheckAPI.class);
        return commitCheckAPI;
    }

}

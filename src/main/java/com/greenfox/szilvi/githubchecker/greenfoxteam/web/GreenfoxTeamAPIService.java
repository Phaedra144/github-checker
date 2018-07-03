package com.greenfox.szilvi.githubchecker.greenfoxteam.web;

import com.greenfox.szilvi.githubchecker.retrofit.GitHubRetrofit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreenfoxTeamAPIService {

    @Autowired
    GitHubRetrofit gitHubRetrofit;

    GreenfoxTeamAPI greenfoxTeamAPI;

    public GreenfoxTeamAPI getGreenfoxTeamAPI() {
        greenfoxTeamAPI = gitHubRetrofit.getRetrofit().create(GreenfoxTeamAPI.class);
        return greenfoxTeamAPI;
    }
}

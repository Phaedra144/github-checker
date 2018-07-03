package com.greenfox.szilvi.githubchecker.greenfoxteam.web;

import com.greenfox.szilvi.githubchecker.httpconnection.GitHubRetrofit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreenfoxTeamAPIService {

    @Autowired
    GitHubRetrofit gitHubRetrofit;

    GreenfoxTeamAPI greenfoxTeamAPI = gitHubRetrofit.getRetrofit().create(GreenfoxTeamAPI.class);

    public GreenfoxTeamAPI getGreenfoxTeamAPI() {
        return greenfoxTeamAPI;
    }
}

package com.greenfox.szilvi.githubchecker.retrofit.newrf;

import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamResponse;

public class GfTeamAPI extends ApiBinding {

    public GfTeamAPI(String accessToken) {
        super(accessToken);
    }

    public GreenfoxTeamResponse getTeamsOfOrg() {
        return restTemplate.getForObject("https://api.github.com/orgs/green-fox-academy/teams",
     GreenfoxTeamResponse.class);
    }
}

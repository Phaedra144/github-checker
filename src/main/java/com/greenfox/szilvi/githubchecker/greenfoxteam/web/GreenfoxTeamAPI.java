package com.greenfox.szilvi.githubchecker.greenfoxteam.web;

import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface GreenfoxTeamAPI {

    @PUT("orgs/{org}/memberships/{username}")
    Call<GreenfoxTeamStatus> addMemberToOrg(@Path("org") String org, @Path("username") String username);

    @PUT("teams/{id}/memberships/{username}")
    Call<GreenfoxTeamStatus> addMemberToTeam(@Path("id") int id, @Path("username") String username);

    @GET("orgs/{org}/teams")
    Call<List<GreenfoxTeamResponse>>getTeamsOfOrg(@Path("org") String org);
}

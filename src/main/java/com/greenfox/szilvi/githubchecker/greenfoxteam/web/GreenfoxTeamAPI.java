package com.greenfox.szilvi.githubchecker.greenfoxteam.web;

import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface GreenfoxTeamAPI {

    @PUT("orgs/{org}/memberships/{username}")
    Call<GreenfoxTeamStatus> addMemberToOrg(@Header("Authorization") String token, @Path("org") String org, @Path("username") String username);

    @PUT("teams/{id}/memberships/{username}")
    Call<GreenfoxTeamStatus> addMemberToTeam(@Header("Authorization") String token, @Path("id") int id, @Path("username") String username);

    @GET("orgs/{org}/teams")
    Call<List<GreenfoxTeamResponse>>getTeamsOfOrg(@Header("Authorization") String token, @Path("org") String org);
}

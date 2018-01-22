package com.greenfox.szilvi.githubchecker.httpconnection;

import com.greenfox.szilvi.githubchecker.models.GfCommits;
import com.greenfox.szilvi.githubchecker.models.MemberStatusResponse;
import com.greenfox.szilvi.githubchecker.models.TeamResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;


public interface GitHubAPIService {

    @GET("repos/{owner}/{repo}/commits?&per_page=100")
    Call<List<GfCommits>> getClassCommits(@Path("owner") String owner, @Path("repo") String repo, @Query("since") String startDate, @Query("until") String endDate);

    @PUT("orgs/{org}/memberships/{username}")
    Call<MemberStatusResponse> addMemberToOrg(@Path("org") String org, @Path("username") String username);

    @PUT("/teams/{id}/memberships/{username}")
    Call<MemberStatusResponse> addMemberToTeam(@Path("id") int id, @Path("username") String username);

    @GET("/orgs/{org}/teams")
    Call<List<TeamResponse>>getTeamsOfOrg(@Path("org") String org);

}
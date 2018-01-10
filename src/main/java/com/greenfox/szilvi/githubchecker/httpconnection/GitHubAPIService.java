package com.greenfox.szilvi.githubchecker.httpconnection;

import com.greenfox.szilvi.githubchecker.models.GfCommits;
import com.greenfox.szilvi.githubchecker.models.MemberStatusResponse;
import com.greenfox.szilvi.githubchecker.models.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
public interface GitHubAPIService {

    @GET("orgs/{org}/repos")
    Call<List<Repo>> getOrgRepos(@Path("org") String org);

    @GET("repos/{owner}/{repo}/commits?&per_page=50")
    Call<List<GfCommits>> getClassCommits(@Path("owner") String owner, @Path("repo") String repo, @Query("since") String startDate, @Query("until") String endDate);

    @PUT("orgs/{org}/memberships/{username}")
    Call<MemberStatusResponse> addMemberToOrg(@Path("org") String org, @Path("username") String username);

    @GET("orgs/{org}/members/{username}")
    Call<MemberStatusResponse> isMemberOfOrg(@Path("org") String org, @Path("username") String username);

    @PUT("/teams/{id}/memberships/{username}")
    Call<MemberStatusResponse> addMemberToTeam(@Path("id") int id, @Path("username") String username);


}
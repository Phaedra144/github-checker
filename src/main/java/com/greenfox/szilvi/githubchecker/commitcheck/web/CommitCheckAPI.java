package com.greenfox.szilvi.githubchecker.commitcheck.web;

import com.greenfox.szilvi.githubchecker.commitcheck.model.Comment;
import com.greenfox.szilvi.githubchecker.commitcheck.model.ForkedRepo;
import com.greenfox.szilvi.githubchecker.commitcheck.model.GfCommits;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface CommitCheckAPI {

    @GET("repos/{owner}/{repo}/commits?&per_page=100")
    Call<List<GfCommits>> getRepoCommitsForPeriod(@Header("Authorization") String token, @Path("owner") String owner, @Path("repo") String repo, @Query("since") String startDate, @Query("until") String endDate);

    @GET("repos/{owner}/{repo}/commits?&per_page=100")
    Call<List<GfCommits>> getRepoCommits(@Header("Authorization") String token, @Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/comments?&per_page=150")
    Call<List<Comment>>getCommentsOnRepos(@Header("Authorization") String token, @Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/forks?&per_page=150")
    Call<List<ForkedRepo>>getForkedRepos(@Header("Authorization") String token, @Path("owner") String owner, @Path("repo") String repo);
}

package com.greenfox.szilvi.githubchecker.commitcheck.web;

import com.greenfox.szilvi.githubchecker.commitcheck.web.dto.Comment;
import com.greenfox.szilvi.githubchecker.commitcheck.web.dto.ForkedRepo;
import com.greenfox.szilvi.githubchecker.commitcheck.web.dto.CommitsDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface CommitCheckAPI {

    @GET("repos/{owner}/{repo}/commits?&per_page=100")
    Call<List<CommitsDTO>> getRepoCommitsForPeriod(@Path("owner") String owner, @Path("repo") String repo, @Query("since") String startDate, @Query("until") String endDate);

    @GET("repos/{owner}/{repo}/commits?&per_page=100")
    Call<List<CommitsDTO>> getRepoCommits(@Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/comments?&per_page=150")
    Call<List<Comment>>getCommentsOnRepos(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/forks?&per_page=150")
    Call<List<ForkedRepo>>getForkedRepos(@Path("owner") String owner, @Path("repo") String repo);
}

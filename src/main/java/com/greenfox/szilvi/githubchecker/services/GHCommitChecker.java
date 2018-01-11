package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.httpconnection.GitHubRetrofit;
import com.greenfox.szilvi.githubchecker.models.GfCommits;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import static com.greenfox.szilvi.githubchecker.services.Settings.*;

/**
 * Created by Szilvi on 2017. 09. 28..
 */
@Service
public class GHCommitChecker {

    GitHubRetrofit gitHubRetrofit = new GitHubRetrofit();
    CheckDates checkDates = new CheckDates();

    public List<String> checkRepos(List<String> ghHandles) throws IOException {
        if(ghHandles.get(0).substring(0, 2).equals(",,")){
            String firstGhHandle = cutFirstChar(ghHandles.get(0));
            ghHandles.remove(0);
            ghHandles.add(0, firstGhHandle);
        }
        return ghHandles;
    }

    private String cutFirstChar(String firstGhHandle) {
        return firstGhHandle.substring(2);
    }

    public void fillNotCommittedDays(HashMap<String, Integer> notCommittedDays, List<String> classRepos, String startDate, String endDate) throws IOException {
        List<GfCommits> gfCommits;
        for (int i = 0; i < classRepos.size(); i++) {
            String repoName = classRepos.get(i);
            gfCommits = getPreviousWeekCommits(repoName, startDate, endDate);
            int noCommitDays = checkDates.checkHowManyDaysNotCommitted(gfCommits, startDate, endDate);
            notCommittedDays.put(repoName, noCommitDays);
        }
    }

    public List<GfCommits> getPreviousWeekCommits(String repoName, String startDate, String endDate) throws IOException {
        LocalDate endD = checkDates.convertToLocalDate(endDate).plusDays(1);
        endDate = endD.toString();
        Call<List<GfCommits>> gfCommitsCall = gitHubRetrofit.getService().getClassCommits(GITHUB_ORG, repoName, startDate, endDate);
        return gfCommitsCall.execute().body();
    }

}

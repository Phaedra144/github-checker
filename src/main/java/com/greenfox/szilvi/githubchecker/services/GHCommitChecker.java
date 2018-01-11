package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.httpconnection.GitHubRetrofit;
import com.greenfox.szilvi.githubchecker.models.GfCommits;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.*;
import static com.greenfox.szilvi.githubchecker.services.Settings.*;

/**
 * Created by Szilvi on 2017. 09. 28..
 */
@Service
public class GHCommitChecker {

    GitHubRetrofit gitHubRetrofit = new GitHubRetrofit();
    CheckDates checkDates = new CheckDates();

    public List<String> getRepos(String ghHandles) throws IOException {
        String[]ghArray = ghHandles.split(" ");
        List<String> classRepos = new ArrayList<>();
        for (String repo : ghArray) {
            int count = 0;
            List<String> excludeRepos = EXCLUDE_REPOS;
            for (String excRep : excludeRepos) {
                if (repo.contains(excRep)){
                    count++;
                }
            }
            if (count < 1){
                classRepos.add(repo);
            }
        }
        if(classRepos.get(0).substring(0, 2).equals(",,")){
            String firstGhHandle = cutFirstChar(classRepos.get(0));
            classRepos.remove(0);
            classRepos.add(0, firstGhHandle);
        }
        return classRepos;
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
        Call<List<GfCommits>> gfCommitsCall = gitHubRetrofit.getService().getClassCommits(GITHUB_ORG, repoName, startDate, endDate);
        return gfCommitsCall.execute().body();
    }
}

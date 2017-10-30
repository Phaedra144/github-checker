package services;

import httpconnection.GitHubRetrofit;
import models.GfCommits;
import models.Repo;
import models.RepoSearchResult;
import retrofit2.Call;


import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Szilvi on 2017. 09. 28..
 */
public class GitHubService {

    GitHubRetrofit gitHubRetrofit = new GitHubRetrofit();
    RepoSearchResult myClassRepos;
    CheckDates checkDates = new CheckDates();

    public List<Repo> getRepos() throws IOException {
        Call<RepoSearchResult> gfClassRepos = gitHubRetrofit.getService().getSearchedRepos();
        myClassRepos = gfClassRepos.execute().body();
        List<Repo> classRepos = new ArrayList<>();
        for (Repo repo:myClassRepos.getItems()) {
            String repoName = repo.getName();
            if (!((repoName.contains("todo")) || (repoName.contains("RPG") || (repoName.contains("to-do"))))){
                classRepos.add(repo);
            }
        }
        return classRepos;
    }

    public void fillNotCommittedDays(HashMap<String, Integer> notCommittedDays, List<Repo> classRepos) throws IOException {
        List<GfCommits> gfCommits;
        for (int i = 0; i < classRepos.size(); i++) {
            String repoName = classRepos.get(i).getName();
            gfCommits = getPreviousWeekCommits(repoName);
            int noCommitDays = checkHowManyDaysNotCommitted(gfCommits);
            notCommittedDays.put(repoName, noCommitDays);
        }
    }

    public List<GfCommits> getPreviousWeekCommits(String repoName) throws IOException {
        Call<List<GfCommits>> gfCommitsCall = gitHubRetrofit.getService().getClassCommits("greenfox-academy", repoName);
        List<GfCommits> gfCommits = gfCommitsCall.execute().body();
        List<GfCommits> previousWeekCommits = new ArrayList<>();
        for (int i = 0; i < gfCommits.size(); i++) {
            GfCommits gfCommits1 = gfCommits.get(i);
            String date = gfCommits.get(i).getCommit().getAuthor().getDate();
            if (checkDates.isPreviousWeek(date)){
                previousWeekCommits.add(gfCommits1);
            }

        }
        return previousWeekCommits;
    }

    public int checkHowManyDaysNotCommitted(List<GfCommits> gfCommits) {
        int count = 0;
        HashMap<LocalDate, Integer> myMap = checkDates.daysOfNotCommiting(gfCommits);
        for (Map.Entry entry : myMap.entrySet()) {
            if (entry.getValue().equals(Integer.valueOf(0))){
                count++;
            }
        }
        return count;
    }
}
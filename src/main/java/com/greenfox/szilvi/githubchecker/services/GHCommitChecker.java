package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.httpconnection.GitHubRetrofit;
import com.greenfox.szilvi.githubchecker.models.*;
import com.greenfox.szilvi.githubchecker.models.GfCommits;
import com.greenfox.szilvi.githubchecker.models.Repo;
import com.greenfox.szilvi.githubchecker.models.RepoSearchResult;
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
    RepoSearchResult myClassRepos;
    CheckDates checkDates = new CheckDates();

    public List<Repo> getRepos(String gfclass, String gfcohort) throws IOException {
        String query = "topic:" + gfclass + "+" + "topic:" + gfcohort + "&per_page=100";
        Call<RepoSearchResult> gfClassRepos = gitHubRetrofit.getService().getSearchedRepos(query);
        myClassRepos = gfClassRepos.execute().body();
        List<Repo> classRepos = new ArrayList<>();
        for (Repo repo : myClassRepos.getItems()) {
            String repoName = repo.getName();
            int count = 0;
            List<String> excludeRepos = EXCLUDE_REPOS;
            for (String excRep : excludeRepos) {
                if (repoName.contains(excRep)){
                    count++;
                }
            }
            if (count < 1){
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
            int noCommitDays = checkDates.checkHowManyDaysNotCommitted(gfCommits);
            notCommittedDays.put(repoName, noCommitDays);
        }
    }

    public List<GfCommits> getPreviousWeekCommits(String repoName) throws IOException {
        Call<List<GfCommits>> gfCommitsCall = gitHubRetrofit.getService().getClassCommits(GITHUB_ORG, repoName, checkDates.getPreviousWeekStartDate(), checkDates.getPreviousWeekEndDate());
        return gfCommitsCall.execute().body();
    }
}

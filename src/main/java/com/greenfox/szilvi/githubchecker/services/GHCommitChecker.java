package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.entities.ClassGithub;
import com.greenfox.szilvi.githubchecker.httpconnection.GitHubRetrofit;
import com.greenfox.szilvi.githubchecker.models.Comment;
import com.greenfox.szilvi.githubchecker.models.ForkedRepo;
import com.greenfox.szilvi.githubchecker.models.GfCommits;
import com.greenfox.szilvi.githubchecker.repositories.GithubHandleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import static com.greenfox.szilvi.githubchecker.services.Settings.*;


@Service
public class GHCommitChecker {

    @Autowired
    GithubHandleRepo classGithubRepo;

    GitHubRetrofit gitHubRetrofit = new GitHubRetrofit();
    CheckDates checkDates = new CheckDates();

    public List<String> checkRepos(List<String> ghHandles) {
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

    public HashMap<String, List<Integer>> fillMapWithRepoRelevantStats(List<String> classRepos, String startDate, String endDate, String language) throws IOException {
        HashMap<String, List<Integer>> githubThingsHashMap = new HashMap<>();
        for (int i = 0; i < classRepos.size(); i++) {
            List<Integer> counts = new ArrayList<>();
            long startTime = System.nanoTime();
            int noCommitDays = checkDates.checkHowManyDaysNotCommitted(getPreviousWeekCommits(classRepos.get(i), startDate, endDate), startDate, endDate);
            long noCommit = System.nanoTime() - startTime;
            long noCommitStart = System.nanoTime();
            System.out.println("No commit: " + noCommit / 1000000000.0);
            int gfCommits = getPreviousWeekCommits(classRepos.get(i), startDate, endDate).size();
            long commits = System.nanoTime() - noCommitStart;
            long commitsStart = System.nanoTime();
            System.out.println("Commits: " + commits / 1000000000.0);
            int gfComments = getComments(classRepos.get(i)).size();
            long comments = System.nanoTime() - commitsStart;
            long commentStart = System.nanoTime();
            System.out.println("Comments: " + comments / 1000000000.0);
            int todoCommits = getHashMapCommits(getExtraReposAndOwners(language, TODO_APP), classRepos.get(i)).size();
            long todoCommit = System.nanoTime() - commentStart;
            long todoCommitStart = System.nanoTime();
            System.out.println("Todocommits: " + todoCommit / 1000000000.0);
            int wandererCommits = getHashMapCommits(getExtraReposAndOwners(language, WANDERER), classRepos.get(i)).size();
            long wanderer = System.nanoTime() - todoCommitStart;
            System.out.println("Wanderer commits: " + wanderer / 1000000000.0);
            counts.add(noCommitDays);
            counts.add(gfCommits);
            counts.add(gfComments);
            counts.add(todoCommits);
            counts.add(wandererCommits);
            githubThingsHashMap.put(classRepos.get(i), counts);
        }
        return githubThingsHashMap;
    }

    public List<GfCommits> getPreviousWeekCommits(String repoName, String startDate, String endDate) throws IOException {
        startDate = getStringStartDate(startDate);
        endDate = getStringEndDate(endDate);
        Call<List<GfCommits>> gfCommitsCall = gitHubRetrofit.getService().getRepoCommitsForPeriod(GITHUB_ORG, repoName, startDate, endDate);
        return gfCommitsCall.execute().body();
    }

    public List<Comment> getComments(String repoName) throws IOException {
        Call<List<Comment>> gfComments = gitHubRetrofit.getService().getCommentsOnRepos(GITHUB_ORG, repoName);
        return gfComments.execute().body();
    }

    public List<GfCommits> getHashMapCommits(HashMap<String, String> inputHashMap, String repo) throws IOException {
        for (Map.Entry entry : inputHashMap.entrySet()) {
            if (((String)entry.getKey()).equals(repo)){
                Call<List<GfCommits>> gfCommitsCall = gitHubRetrofit.getService().getRepoCommits((String)entry.getKey(), (String)entry.getValue());
                return gfCommitsCall.execute().body();
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<Integer> getTotalStats(HashMap<String, List<Integer>> repoHashMap, int numberOfStats) {
        ArrayList<Integer> totals = new ArrayList<>();
        for (int i = 0; i < numberOfStats; i++) {
            int count = 0;
            for (Map.Entry entry : repoHashMap.entrySet()) {
                List<Integer> counts = (List<Integer>) entry.getValue();
                count = count + counts.get(i);
            }
            totals.add(count);
        }
        return totals;
    }

    private HashMap<String, String> getExtraReposAndOwners(String language, String repoType) throws IOException {
        Call<List<ForkedRepo>> gfForked = gitHubRetrofit.getService().getForkedRepos(GITHUB_ORG, getRepoType(repoType, language));
        List<ForkedRepo> forkedRepos = gfForked.execute().body();
        HashMap<String, String> ownersAndRepos = new HashMap<>();
        for (ForkedRepo forkedRepo : forkedRepos) {
            if (forkedRepo.getLanguage() != null && forkedRepo.getLanguage().equals(language)){
                String[] repoNameParts = forkedRepo.getFull_name().split("/");
                ownersAndRepos.put(repoNameParts[0], repoNameParts[1]);
            }
        }
        return ownersAndRepos;
    }

    public List<String> ghHandlesToString(List<ClassGithub> ghHandlesByClass) {
        List<String> ghHandles = new ArrayList<>();
        for (ClassGithub ch:ghHandlesByClass) {
            ghHandles.add(ch.getGithubHandle());
        }
        return ghHandles;
    }

    private String getStringEndDate(String endDate) {
        LocalDate endD = checkDates.convertToLocalDate(endDate).plusDays(1);
        endDate = endD.toString();
        return endDate;
    }

    private String getStringStartDate(String startDate) {
        LocalDate startD = checkDates.convertToLocalDate(startDate).minusDays(1);
        startDate = startD.toString();
        return startDate;
    }

    public String getGfLanguage(String gfclass) {
        String language = "";
        if (gfclass.equals("becool") || gfclass.equals("please")){
            language = "Java";
        } else if (gfclass.equals("pebble") || gfclass.equals("asbest")){
            language = "C#";
        } else if (gfclass.equals("ace")) {
            language = "TypeScript";
        } else if (gfclass.equals("badcat")){
            language = "JavaScript";
        }
        return language;
    }

    public String getRepoType(String repoType, String language) {
        String transformedLanguage = "";
        if (repoType.equals("wanderer-")) {
            switch (language.toLowerCase()) {
                case "java" :
                    transformedLanguage = "java";
                    break;
                case "c#" :
                    transformedLanguage = "cs";
                    break;
                case "typescript" :
                    transformedLanguage = "typescript";
            }
            return repoType + transformedLanguage;
        }
        return repoType;
    }
}

package com.greenfox.szilvi.githubchecker.commitcheck.service;

import com.greenfox.szilvi.githubchecker.commitcheck.web.CommitCheckAPIService;
import com.greenfox.szilvi.githubchecker.githubhandles.persistance.entity.ClassGithub;
import com.greenfox.szilvi.githubchecker.commitcheck.model.Comment;
import com.greenfox.szilvi.githubchecker.commitcheck.model.ForkedRepo;
import com.greenfox.szilvi.githubchecker.commitcheck.model.GfCommits;
import com.greenfox.szilvi.githubchecker.githubhandles.persistance.dao.GithubHandleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import static com.greenfox.szilvi.githubchecker.general.Settings.*;


@Service
public class CommitCheckService {

    @Autowired
    GithubHandleRepo classGithubRepo;

    @Autowired
    CommitCheckAPIService commitCheckAPIService;

    @Autowired
    CheckDates checkDates;

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

    public HashMap<String, List<Integer>> fillMapWithRepoRelevantStats(String token, List<String> classRepos, String startDate, String endDate, String language, boolean isTodo, boolean isWanderer) throws IOException {

        HashMap<String, List<Integer>> githubThingsHashMap = new HashMap<>();
        for (int i = 0; i < classRepos.size(); i++) {
            List<Integer> counts = new ArrayList<>();

            List<GfCommits> gfCommits = getPreviousWeekCommits(token, classRepos.get(i), startDate, endDate);

            int noCommitDays = checkDates.checkHowManyDaysNotCommitted(gfCommits, startDate, endDate);
            int gfCommitsSize = gfCommits == null ? 0 : gfCommits.size();

            int gfComments = getComments(token, classRepos.get(i)).size();

            int todoCommits = 0;

            if (isTodo) todoCommits = getHashMapCommits(token, getExtraReposAndOwners(token, language, TODO_APP), classRepos.get(i)).size();

            int wandererCommits = 0;
            if (isWanderer) wandererCommits = getHashMapCommits(token, getExtraReposAndOwners(token, language, WANDERER), classRepos.get(i)).size();

            counts.add(noCommitDays);
            counts.add(gfCommitsSize);
            counts.add(gfComments);
            counts.add(todoCommits);
            counts.add(wandererCommits);
            
            githubThingsHashMap.put(classRepos.get(i), counts);
        }
        return githubThingsHashMap;
    }

    public List<GfCommits> getPreviousWeekCommits(String token, String repoName, String startDate, String endDate) throws IOException {
        startDate = getStringStartDate(startDate);
        endDate = getStringEndDate(endDate);
        Call<List<GfCommits>> gfCommitsCall = commitCheckAPIService.getCommitCheckAPI().getRepoCommitsForPeriod(token, GITHUB_ORG, repoName, startDate, endDate);
        return gfCommitsCall.execute().body();
    }

    public List<Comment> getComments(String token, String repoName) throws IOException {
        Call<List<Comment>> gfComments = commitCheckAPIService.getCommitCheckAPI().getCommentsOnRepos(token, GITHUB_ORG, repoName);
        return gfComments.execute().body();
    }

    public List<GfCommits> getHashMapCommits(String token, HashMap<String, String> inputHashMap, String repo) throws IOException {
        for (Map.Entry entry : inputHashMap.entrySet()) {
            if (((String)entry.getKey()).equals(repo)){
                Call<List<GfCommits>> gfCommitsCall = commitCheckAPIService.getCommitCheckAPI().getRepoCommits(token, (String)entry.getKey(), (String)entry.getValue());
                return gfCommitsCall.execute().body();
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<Integer> getTotalStats(HashMap<String, List<Integer>> repoHashMap, int numberOfStats, boolean isTodo, boolean isWanderer) {
        ArrayList<Integer> totals = new ArrayList<>();
        if (isTodo) numberOfStats++;
        if (isWanderer) numberOfStats++;
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



    private HashMap<String, String> getExtraReposAndOwners(String token, String language, String repoType) throws IOException {
        Call<List<ForkedRepo>> gfForked = commitCheckAPIService.getCommitCheckAPI().getForkedRepos(token, GITHUB_ORG, getRepoType(repoType, language));
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

    public List<ClassGithub> findAllByClassName(String gfclass) {
        return classGithubRepo.findAllByClassName(gfclass);
    }

    public List<String> getDistinctClasses() {
        return classGithubRepo.getDistinctClasses();
    }
}

package com.greenfox.szilvi.githubchecker.commitcheck.service;

import com.greenfox.szilvi.githubchecker.commitcheck.web.dto.Comment;
import com.greenfox.szilvi.githubchecker.commitcheck.web.dto.ForkedRepo;
import com.greenfox.szilvi.githubchecker.commitcheck.web.dto.CommitsDTO;
import com.greenfox.szilvi.githubchecker.commitcheck.web.CommitCheckAPIService;
import com.greenfox.szilvi.githubchecker.greenfoxteam.persistance.dao.GithubHandleRepo;
import com.greenfox.szilvi.githubchecker.greenfoxteam.persistance.entity.ClassGithub;
import com.greenfox.szilvi.githubchecker.greenfoxteam.service.GithubHandleParser;
import com.greenfox.szilvi.githubchecker.greenfoxteam.service.GreenfoxDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;


@Service
public class CommitCheckService {

    @Autowired
    GreenfoxDbService greenfoxDbService;

    @Autowired
    CommitCheckAPIService commitCheckAPIService;

    @Autowired
    CheckDates checkDates;

    @Autowired
    GithubHandleParser githubHandleParser;

    public HashMap<String, List<Integer>> fillMapWithRepoRelevantStats(List<String> classRepos, String startDate, String endDate, boolean isTodo, boolean isWanderer) throws IOException {

        HashMap<String, List<Integer>> githubThingsHashMap = new HashMap<>();
        for (int i = 0; i < classRepos.size(); i++) {
            try {
                List<Integer> counts = new ArrayList<>();
                String classRepo = classRepos.get(i);
                List<CommitsDTO> commitsDTO = getPreviousWeekCommits(classRepo, startDate, endDate);
                String language = greenfoxDbService.getStudentFromDB(classRepo).getLanguage();
                int noCommitDays = checkDates.checkHowManyDaysNotCommitted(commitsDTO, startDate, endDate);
                int gfCommitsSize = commitsDTO == null ? 0 : commitsDTO.size();

                int gfComments = getComments(classRepo).size();

                int todoCommits = 0;

                if (isTodo)
                    todoCommits = getHashMapCommits(getExtraReposAndOwners(language, TODO_APP), classRepo).size();

                int wandererCommits = 0;
                if (isWanderer)
                    wandererCommits = getHashMapCommits(getExtraReposAndOwners(language, WANDERER), classRepo).size();

                counts.add(noCommitDays);
                counts.add(gfCommitsSize);
                counts.add(gfComments);
                counts.add(todoCommits);
                counts.add(wandererCommits);

                githubThingsHashMap.put(classRepo, counts);
            } catch (Exception e) {
                System.out.println("There was a problem with this github handle: " + classRepos.get(i));
            }
        }
        return githubThingsHashMap;
    }

    public List<CommitsDTO> getPreviousWeekCommits(String repoName, String startDate, String endDate) throws IOException {
        startDate = getStringStartDate(startDate);
        endDate = getStringEndDate(endDate);
        Call<List<CommitsDTO>> gfCommitsCall = commitCheckAPIService.getCommitCheckAPI().getRepoCommitsForPeriod(GITHUB_ORG, repoName, startDate, endDate);
        System.out.println(gfCommitsCall.request().headers());
        return gfCommitsCall.execute().body();
    }

    public List<Comment> getComments(String repoName) throws IOException {
        Call<List<Comment>> gfComments = commitCheckAPIService.getCommitCheckAPI().getCommentsOnRepos(GITHUB_ORG, repoName);
        return gfComments.execute().body();
    }

    public List<CommitsDTO> getHashMapCommits(HashMap<String, String> inputHashMap, String repo) throws IOException {
        for (Map.Entry entry : inputHashMap.entrySet()) {
            if (((String)entry.getKey()).equals(repo)){
                Call<List<CommitsDTO>> gfCommitsCall = commitCheckAPIService.getCommitCheckAPI().getRepoCommits((String)entry.getKey(), (String)entry.getValue());
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

    private HashMap<String, String> getExtraReposAndOwners(String language, String repoType) throws IOException {
        Call<List<ForkedRepo>> gfForked = commitCheckAPIService.getCommitCheckAPI().getForkedRepos(GITHUB_ORG, githubHandleParser.getRepoType(repoType, language));
        List<ForkedRepo> forkedRepos = gfForked.execute().body();
        HashMap<String, String> ownersAndRepos = new HashMap<>();
        for (ForkedRepo forkedRepo : forkedRepos) {
            if (forkedRepo.getLanguage() != null && forkedRepo.getLanguage().equals(language)) {
                String[] repoNameParts = forkedRepo.getFull_name().split("/");
                ownersAndRepos.put(repoNameParts[0], repoNameParts[1]);
            }
        }
        return ownersAndRepos;
    }

    public List<String> ghHandlesToString(List<ClassGithub> ghHandlesByClass) {
        List<String> ghHandles = new ArrayList<>();
        for (ClassGithub ch : ghHandlesByClass) {
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
}

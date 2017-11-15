package services;

import httpconnection.GitHubRetrofit;
import models.*;
import retrofit2.Call;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by Szilvi on 2017. 09. 28..
 */
public class GitHubService {

    GitHubRetrofit gitHubRetrofit = new GitHubRetrofit();
    RepoSearchResult myClassRepos;
    CheckDates checkDates = new CheckDates();
    FileHandling fileHandling = new FileHandling();
    static String GITHUB_ORG = "greenfox-academy";
    static int CORSAC_ID = 2555784;

    public List<Repo> getRepos() throws IOException {
        Call<RepoSearchResult> gfClassRepos = gitHubRetrofit.getService().getSearchedRepos();
        myClassRepos = gfClassRepos.execute().body();
        List<Repo> classRepos = new ArrayList<>();
        for (Repo repo : myClassRepos.getItems()) {
            String repoName = repo.getName();
            if (!(repoName.contains("todo") || repoName.contains("RPG") || repoName.contains("to-do"))){
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

    public void addNewMembersToGf(String filename) throws IOException {
        List<String> ghHandles = fileHandling.readFile(filename);
        List<MemberStatusResponse> memberStatusResponseList = new ArrayList<>();
        callingToAddMembers(ghHandles, memberStatusResponseList);
        for (MemberStatusResponse status : memberStatusResponseList) {
            System.out.println(status);
        }
    }

    private void callingToAddMembers(List<String> ghHandles, List<MemberStatusResponse> memberStatusResponseList) throws IOException {
        String shortGhHandle = "";
        for (int i = 0; i < ghHandles.size(); i++) {
            if(i > 0){
                shortGhHandle = fileHandling.trunkTheLastPart(ghHandles.get(i));
                if(!isMemberOfOrg(shortGhHandle)){
                    Call<MemberStatusResponse> addingMemberResponse = gitHubRetrofit.getService().addMemberToOrg(GITHUB_ORG, shortGhHandle);
                    MemberStatusResponse memberStatusResponse = addingMemberResponse.execute().body();
                    checkStatusAndAddToList(memberStatusResponseList, memberStatusResponse, shortGhHandle);
                }
                Call<MemberStatusResponse> addMemberToTeam = gitHubRetrofit.getService().addMemberToTeam(CORSAC_ID, shortGhHandle);
                MemberStatusResponse memberStatusResponse = addMemberToTeam.execute().body();
                checkStatusAndAddToList(memberStatusResponseList, memberStatusResponse, shortGhHandle);
            }

        }
    }

    private void checkStatusAndAddToList(List<MemberStatusResponse> memberStatusResponseList, MemberStatusResponse memberStatusResponse, String ghHandle) {
        if (memberStatusResponse != null){
            memberStatusResponse.setHttpStatus("ok");
            memberStatusResponse.setGhHandle(ghHandle);
        } else{
            memberStatusResponse = new MemberStatusResponse("error", ghHandle);
        }
        memberStatusResponseList.add(memberStatusResponse);
    }

    public boolean isMemberOfOrg(String ghHandle) throws IOException {
        URL url = new URL("https://api.github.com/orgs/" + GITHUB_ORG + "/members/" + ghHandle);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Authorization", System.getenv("COMMIT_CHECKER"));
        http.setRequestProperty("Accept", "application/vnd.github.v3+json");
        int code = http.getResponseCode();
        return code == 204;
    }
}

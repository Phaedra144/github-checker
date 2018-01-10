package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.httpconnection.GitHubRetrofit;
import com.greenfox.szilvi.githubchecker.models.MemberStatusResponse;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.greenfox.szilvi.githubchecker.services.Settings.*;

@Service
public class AddGHMembers {

    FileHandling fileHandling = new FileHandling();
    GitHubRetrofit gitHubRetrofit = new GitHubRetrofit();

    public List<MemberStatusResponse> addNewMembersToGf(String filename) throws IOException {
        List<String> ghHandles = new ArrayList<String>(Arrays.asList(filename.split(" ")));
        List<MemberStatusResponse> memberStatusResponseList = new ArrayList<>();
        callingToAddMembers(ghHandles, memberStatusResponseList);
        return  memberStatusResponseList;
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
                Call<MemberStatusResponse> addMemberToTeam = gitHubRetrofit.getService().addMemberToTeam(COHORT_ID, shortGhHandle);
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
        http.setRequestProperty("Authorization", System.getProperty(GITHUB_TOKEN));
        http.setRequestProperty("Accept", "application/vnd.github.v3+json");
        int code = http.getResponseCode();
        return code == 204;
    }
}

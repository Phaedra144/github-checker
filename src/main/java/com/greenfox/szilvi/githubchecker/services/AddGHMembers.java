package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.httpconnection.GitHubRetrofit;
import com.greenfox.szilvi.githubchecker.models.MemberStatusResponse;
import com.greenfox.szilvi.githubchecker.models.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.greenfox.szilvi.githubchecker.services.Settings.*;

@Service
public class AddGHMembers {

    @Autowired
    GitHubRetrofit gitHubRetrofit;

    public List<MemberStatusResponse> addNewMembersToGf(String members, String teamName) throws IOException {
        List<String> ghHandles = new ArrayList<String>(Arrays.asList(members.split(" ")));
        List<MemberStatusResponse> memberStatusResponseList = new ArrayList<>();
        callingToAddMembersToOrgAndTeam(ghHandles, teamName, memberStatusResponseList);
        return  memberStatusResponseList;
    }

    private void callingToAddMembersToOrgAndTeam(List<String> ghHandles, String teamName, List<MemberStatusResponse> memberStatusResponseList) throws IOException {
        for (String ghHandle : ghHandles){
            Call<MemberStatusResponse> addingMemberResponse = gitHubRetrofit.getService().addMemberToOrg(GITHUB_ORG, ghHandle);
            MemberStatusResponse memberStatusResponse = addingMemberResponse.execute().body();
            checkStatusAndAddToList(memberStatusResponseList, memberStatusResponse, ghHandle);

            Call<MemberStatusResponse> addMemberToTeam = gitHubRetrofit.getService().addMemberToTeam(getIdOfTeam(teamName), ghHandle);
            MemberStatusResponse memberStatusResponseToTeam = addMemberToTeam.execute().body();
            checkStatusAndAddToList(memberStatusResponseList, memberStatusResponseToTeam, ghHandle);
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

    private int getIdOfTeam(String teamName) throws IOException {
        Call<List<TeamResponse>> getTeams = gitHubRetrofit.getService().getTeamsOfOrg(GITHUB_ORG);
        List<TeamResponse> gfTeams = getTeams.execute().body();
        int teamId = 0;
        for (TeamResponse gfTeam : gfTeams){
            if (gfTeam.getName().equals(teamName)){
                teamId = gfTeam.getId();
            }
        }
        return teamId;
    }
}

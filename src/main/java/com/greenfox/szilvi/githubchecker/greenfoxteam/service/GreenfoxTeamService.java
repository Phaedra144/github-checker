package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.greenfoxteam.web.GreenfoxTeamAPIService;
import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamResponse;
import com.greenfox.szilvi.githubchecker.githubhandles.service.HandlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;

@Service
public class GreenfoxTeamService {

    @Autowired
    HandlesService handlesService;

    @Autowired
    GreenfoxTeamAPIService greenfoxTeamAPIService;

    public List<GreenfoxTeamStatus> addNewMembersToGf(String token, String members, String teamName) throws IOException {
        List<String> ghHandles = handlesService.handleListOfHandles(members);
        List<GreenfoxTeamStatus> memberStatusResponseList = new ArrayList<>();
        callingToAddMembersToOrgAndTeam(ghHandles, teamName, memberStatusResponseList, token);
        return  memberStatusResponseList;
    }

    private void callingToAddMembersToOrgAndTeam(List<String> ghHandles, String teamName, List<GreenfoxTeamStatus> memberStatusResponseList, String token) throws IOException {
        for (String ghHandle : ghHandles){
            Call<GreenfoxTeamStatus> addingMemberResponse = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToOrg(token, GITHUB_ORG, ghHandle);
            GreenfoxTeamStatus memberStatusResponse = addingMemberResponse.execute().body();
            checkStatusAndAddToList(memberStatusResponseList, memberStatusResponse, ghHandle);

            Call<GreenfoxTeamStatus> addMemberToTeam = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToTeam(token, getIdOfTeam(token, teamName), ghHandle);
            GreenfoxTeamStatus memberStatusResponseToTeam = addMemberToTeam.execute().body();
            checkStatusAndAddToList(memberStatusResponseList, memberStatusResponseToTeam, ghHandle);
        }
    }

    private void checkStatusAndAddToList(List<GreenfoxTeamStatus> memberStatusResponseList, GreenfoxTeamStatus memberStatusResponse, String ghHandle) {
        if (memberStatusResponse != null){
            memberStatusResponse.setHttpStatus("ok");
            memberStatusResponse.setGhHandle(ghHandle);
        } else{
            memberStatusResponse = new GreenfoxTeamStatus("error", ghHandle);
        }
        memberStatusResponseList.add(memberStatusResponse);
    }

    private int getIdOfTeam(String token, String teamName) throws IOException {
        Call<List<GreenfoxTeamResponse>> getTeams = greenfoxTeamAPIService.getGreenfoxTeamAPI().getTeamsOfOrg(token, GITHUB_ORG);
        List<GreenfoxTeamResponse> gfTeams = getTeams.execute().body();
        int teamId = 0;
        for (GreenfoxTeamResponse gfTeam : gfTeams){
            if (gfTeam.getName().equals(teamName)){
                teamId = gfTeam.getId();
            }
        }
        return teamId;
    }
}

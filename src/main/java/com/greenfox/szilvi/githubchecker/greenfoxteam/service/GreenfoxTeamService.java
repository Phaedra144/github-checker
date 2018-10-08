package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import com.greenfox.szilvi.githubchecker.greenfoxteam.web.dto.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.greenfoxteam.web.GreenfoxTeamAPIService;
import com.greenfox.szilvi.githubchecker.greenfoxteam.web.dto.GreenfoxTeamResponse;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
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
    GreenfoxDbService greenfoxDbService;

    @Autowired
    GreenfoxTeamAPIService greenfoxTeamAPIService;

    @Autowired
    GithubHandleParser githubHandleParser;

    @Autowired
    UserHandling userHandling;

    public List<GreenfoxTeamStatus> addNewMembersToGf(String members, String cohortName, String className) throws IOException {
        List<String> ghHandles = githubHandleParser.handleListOfHandles(members);
        List<GreenfoxTeamStatus> memberStatusResponseList = new ArrayList<>();
        String teamName = handleTeamName(cohortName, className);
        String token = "token " + userHandling.findLastAuth().getAccessToken();
        callingToAddMembersToOrgAndTeam(token, ghHandles, teamName, memberStatusResponseList);
        return  memberStatusResponseList;
    }

    private String handleTeamName(String cohortName, String className) {
        String teamName = cohortName + "-" + className;
        if (className.equals("")) {
            teamName = cohortName;
        } else if (cohortName.equals("")) {
            teamName = className;
        }
        return teamName;
    }

    private void callingToAddMembersToOrgAndTeam(String token, List<String> ghHandles, String teamName, List<GreenfoxTeamStatus> memberStatusResponseList) throws IOException {
        for (String ghHandle : ghHandles){
            addMemberToOrg(token, memberStatusResponseList, ghHandle);
            try {
                addMemberToTeam(token, teamName, memberStatusResponseList, ghHandle);
            } catch (NullPointerException nullpointer) {
                System.out.println("Something is not ok with your team name!");
            }
        }
    }

    private void addMemberToOrg(String token, List<GreenfoxTeamStatus> memberStatusResponseList, String ghHandle) throws IOException {
        Call<GreenfoxTeamStatus> addingMemberResponse = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToOrg(token, GITHUB_ORG, ghHandle);
        GreenfoxTeamStatus memberStatusResponse = addingMemberResponse.execute().body();
        checkStatusAndAddToList(memberStatusResponseList, memberStatusResponse, ghHandle);
    }

    private void addMemberToTeam(String token, String teamName, List<GreenfoxTeamStatus> memberStatusResponseList, String ghHandle) throws IOException {
        Call<GreenfoxTeamStatus> addMemberToTeam = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToTeam(token, getIdOfTeam(token, teamName), ghHandle);
        GreenfoxTeamStatus memberStatusResponseToTeam = addMemberToTeam.execute().body();
        checkStatusAndAddToList(memberStatusResponseList, memberStatusResponseToTeam, ghHandle);
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

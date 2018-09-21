package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.greenfoxteam.web.GreenfoxTeamAPIService;
import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;

@Service
public class GreenfoxTeamService {

    @Autowired
    GreenfoxDbService greenfoxDbService;

    @Autowired
    GreenfoxTeamAPIService greenfoxTeamAPIService;

    public List<String> handleListOfHandles(String ghHandles){
        String splitter = "";
        for(char strChar:ghHandles.toCharArray()){
            if(!splitter.contains(" ")){
                if(!splitter.contains("\r") || !splitter.contains("\n")){
                    if (strChar == ' ' || strChar == '\r' || strChar == '\n'){
                        splitter = splitter + strChar;
                    }
                }
            }
        }
        ArrayList<String> theHandles = new ArrayList<String>(Arrays.asList(ghHandles.split(splitter)));
        return theHandles.size() > 1 ? theHandles : new ArrayList<>(Arrays.asList(ghHandles));
    }

    public List<GreenfoxTeamStatus> addNewMembersToGf(String members, String cohortName, String className) throws IOException {
        List<String> ghHandles = handleListOfHandles(members);
        List<GreenfoxTeamStatus> memberStatusResponseList = new ArrayList<>();
        String teamName = cohortName + "-" + className;
        callingToAddMembersToOrgAndTeam(ghHandles, teamName, memberStatusResponseList);
        return  memberStatusResponseList;
    }

    private void callingToAddMembersToOrgAndTeam(List<String> ghHandles, String teamName, List<GreenfoxTeamStatus> memberStatusResponseList) throws IOException {
        for (String ghHandle : ghHandles){
            addMemberToOrg(memberStatusResponseList, ghHandle);
            try {
                addMemberToTeam(teamName, memberStatusResponseList, ghHandle);
            } catch (NullPointerException nullpointer) {
                System.out.println("Something is not ok with your team name!");
            }
        }
    }

    private void addMemberToOrg(List<GreenfoxTeamStatus> memberStatusResponseList, String ghHandle) throws IOException {
        Call<GreenfoxTeamStatus> addingMemberResponse = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToOrg(GITHUB_ORG, ghHandle);
        GreenfoxTeamStatus memberStatusResponse = addingMemberResponse.execute().body();
        checkStatusAndAddToList(memberStatusResponseList, memberStatusResponse, ghHandle);
    }

    private void addMemberToTeam(String teamName, List<GreenfoxTeamStatus> memberStatusResponseList, String ghHandle) throws IOException {
        Call<GreenfoxTeamStatus> addMemberToTeam = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToTeam(getIdOfTeam(teamName), ghHandle);
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

    private int getIdOfTeam(String teamName) throws IOException {
        Call<List<GreenfoxTeamResponse>> getTeams = greenfoxTeamAPIService.getGreenfoxTeamAPI().getTeamsOfOrg(GITHUB_ORG);
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

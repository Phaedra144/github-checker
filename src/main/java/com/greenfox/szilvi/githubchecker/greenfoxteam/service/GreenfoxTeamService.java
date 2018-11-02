package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import com.greenfox.szilvi.githubchecker.greenfoxteam.web.GreenfoxTeamAPIService;
import com.greenfox.szilvi.githubchecker.greenfoxteam.web.dto.GreenfoxTeamResponse;
import com.greenfox.szilvi.githubchecker.greenfoxteam.web.dto.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.greenfox.szilvi.githubchecker.general.Settings.GITHUB_ORG;

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
        String token = "token " + userHandling.findLastAuth().getAccessToken();
        int teamId = getIdOfTeam(token, className, cohortName);
        callingToAddMembersToOrgAndTeam(token, ghHandles, teamId, memberStatusResponseList);
        return memberStatusResponseList;
    }

    private void callingToAddMembersToOrgAndTeam(String token, List<String> ghHandles, int teamId, List<GreenfoxTeamStatus> memberStatusResponseList) throws IOException {
        for (String ghHandle : ghHandles) {
            addMemberToOrg(token, memberStatusResponseList, ghHandle);
            try {
                addMemberToTeam(token, teamId, memberStatusResponseList, ghHandle);
            } catch (NullPointerException nullpointer) {
                System.out.println("Something is not ok with your team name!");
            }
        }
    }

    private void addMemberToOrg(String token, List<GreenfoxTeamStatus> memberStatusResponseList, String ghHandle) throws IOException {
        Call<GreenfoxTeamStatus> addingMemberResponse = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToOrg(token, GITHUB_ORG, ghHandle);
        GreenfoxTeamStatus memberStatusResponse = addingMemberResponse.execute().body();
        checkStatusAndAddToList(memberStatusResponseList, memberStatusResponse, ghHandle, "Added to Green Fox organisation.");
    }

    private void addMemberToTeam(String token, int teamId, List<GreenfoxTeamStatus> memberStatusResponseList, String ghHandle) throws IOException {
        GreenfoxTeamStatus memberStatusResponseToTeam = getGreenfoxTeamStatus(token, teamId, ghHandle);
        GreenfoxTeamResponse realGfTeam = getGreenfoxTeamResponse(token, teamId);
        checkStatusAndAddToList(memberStatusResponseList, memberStatusResponseToTeam, ghHandle, "Added to team: " + realGfTeam.getName());
    }

    private GreenfoxTeamStatus getGreenfoxTeamStatus(String token, int teamId, String ghHandle) throws IOException {
        Call<GreenfoxTeamStatus> addMemberToTeam = greenfoxTeamAPIService.getGreenfoxTeamAPI().addMemberToTeam(token, teamId, ghHandle);
        return addMemberToTeam.execute().body();
    }

    private GreenfoxTeamResponse getGreenfoxTeamResponse(String token, int teamId) throws IOException {
        Call<GreenfoxTeamResponse> getTeamBasedOnId = greenfoxTeamAPIService.getGreenfoxTeamAPI().getGfTeam(token, teamId);
        return getTeamBasedOnId.execute().body();
    }

    private void checkStatusAndAddToList(List<GreenfoxTeamStatus> memberStatusResponseList, GreenfoxTeamStatus memberStatusResponse, String ghHandle, String actiontype) {
        if (memberStatusResponse != null) {
            memberStatusResponse.setHttpStatus("ok");
            memberStatusResponse.setGhHandle(ghHandle);
        } else {
            memberStatusResponse = new GreenfoxTeamStatus("error", ghHandle);
        }
        memberStatusResponse.setActionType(actiontype);
        memberStatusResponseList.add(memberStatusResponse);
    }

    private int getIdOfTeam(String token, String className, String cohortName) throws IOException {
        Call<List<GreenfoxTeamResponse>> getTeams = greenfoxTeamAPIService.getGreenfoxTeamAPI().getTeamsOfOrg(token, GITHUB_ORG);
        List<GreenfoxTeamResponse> gfTeams = getTeams.execute().body();
        int teamId = 0;
        Optional<GreenfoxTeamResponse> classOrCohortOpt = getTeam(gfTeams, className);
        if (classOrCohortOpt == null) {
            classOrCohortOpt = getTeam(gfTeams, cohortName);
            if (classOrCohortOpt != null) {
                teamId = classOrCohortOpt.get().getId();
            }
        } else {
            teamId = classOrCohortOpt.get().getId();
        }
        return teamId;
    }

    private Optional<GreenfoxTeamResponse> getTeam(List<GreenfoxTeamResponse> gfTeams, String classOrCohort) {
        Optional<GreenfoxTeamResponse> classOrCohortOpt = Optional.ofNullable(gfTeams.stream()
                .filter(teamName -> teamName.getName().contains(classOrCohort))
                .findFirst()
                .orElse(null));
        return classOrCohortOpt;
    }
}

package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.entities.ClassGithub;
import com.greenfox.szilvi.githubchecker.models.MemberStatusResponse;
import com.greenfox.szilvi.githubchecker.repositories.ClassGithubRepo;
import com.greenfox.szilvi.githubchecker.services.AddGHMembers;
import com.greenfox.szilvi.githubchecker.services.GHCommitChecker;
import com.greenfox.szilvi.githubchecker.services.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    GHCommitChecker ghCommitChecker;

    @Autowired
    AddGHMembers addGHMembers;

    @Autowired
    ClassGithubRepo classGithubRepo;

    @GetMapping("")
    public String getMain(){
        if(System.getProperty(Settings.GITHUB_TOKEN) != null){
            return "index";
        }else {
            return "login";
        }
    }

    @GetMapping("/checkcommit")
    public String getCommitChecker(Model model){
        model.addAttribute("classes", classGithubRepo.getDistinctClasses());
        return "commitchecker";
    }

    @GetMapping("/addmember")
    public String getMemberAdder(){
        return "memberadder";
    }

    @PostMapping("/checkcommit")
    public String checkCommits(@RequestParam String gfclass, @RequestParam String startDate, @RequestParam String endDate, Model model) throws IOException {
        HashMap<String, Integer> notCommittedDays = new HashMap<>();
        List<String> ghHandles = new ArrayList<>();
        List<ClassGithub> ghHandlesByClass = classGithubRepo.findAllByClassName(gfclass);
        for (ClassGithub ch:ghHandlesByClass) {
            ghHandles.add(ch.getGithubHandle());
        }
        List<String> classRepos = ghCommitChecker.getRepos(ghHandles);
        ghCommitChecker.fillNotCommittedDays(notCommittedDays, classRepos, startDate, endDate);

        model.addAttribute("map", notCommittedDays);
        return "commitchecker";
    }

    @PostMapping("/addmember")
    public String addMember(@RequestParam String members, Model model) throws IOException {
        List<MemberStatusResponse> memberStatusResponse = addGHMembers.addNewMembersToGf(members);
        model.addAttribute("responses", memberStatusResponse);
        return "memberadder";
    }
}

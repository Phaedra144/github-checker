package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.entities.ClassGithub;
import com.greenfox.szilvi.githubchecker.repositories.ClassGithubRepo;
import com.greenfox.szilvi.githubchecker.services.Authorization;
import com.greenfox.szilvi.githubchecker.services.GHCommitChecker;
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
public class CommitCheckController {

    @Autowired
    GHCommitChecker ghCommitChecker;

    @Autowired
    ClassGithubRepo classGithubRepo;

    @Autowired
    Authorization authorization;

    @GetMapping(value = {"", "/"})
    public String getMain(){
        return authorization.checkTokenOnPage("index");
    }

    @GetMapping("/checkcommit")
    public String getCommitChecker(Model model){
        model.addAttribute("classes", classGithubRepo.getDistinctClasses());
        return authorization.checkTokenOnPage("commitchecker");
    }

    @PostMapping("/checkcommit")
    public String checkCommits(@RequestParam(required = false) String gfclass, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, Model model) throws IOException {
        if(gfclass.equals("") || startDate.equals("") || endDate.equals("")){
            model.addAttribute("error", "You are missing something, try again!");
            model.addAttribute("classes", classGithubRepo.getDistinctClasses());
            return "commitchecker";
        }
        HashMap<String, Integer> notCommittedDays = new HashMap<>();
        List<String> ghHandles = new ArrayList<>();
        List<ClassGithub> ghHandlesByClass = classGithubRepo.findAllByClassName(gfclass);
        ghCommitChecker.ghHandlesToString(ghHandles, ghHandlesByClass);
        List<String> classRepos = ghCommitChecker.checkRepos(ghHandles);
        ghCommitChecker.fillNotCommittedDays(notCommittedDays, classRepos, startDate, endDate);
        model.addAttribute("classes", classGithubRepo.getDistinctClasses());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("map", notCommittedDays);
        model.addAttribute("sum", ghCommitChecker.getTotal(notCommittedDays));
        return "commitchecker";
    }
}

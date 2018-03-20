package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.repositories.GithubHandleRepo;
import com.greenfox.szilvi.githubchecker.services.Authorization;
import com.greenfox.szilvi.githubchecker.services.GHCommitChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class CommitCheckController {

    @Autowired
    GHCommitChecker ghCommitChecker;

    @Autowired
    GithubHandleRepo classGithubRepo;

    @Autowired
    Authorization authorization;

    @GetMapping(value = {"", "/"})
    public String getMain() {
        return authorization.checkTokenOnPage("index");
    }

    @GetMapping("/checkcommit")
    public String getCommitChecker(Model model) {
        model.addAttribute("classes", classGithubRepo.getDistinctClasses());
        return authorization.checkTokenOnPage("commitchecker");
    }

    @PostMapping("/checkcommit")
    public String checkCommits(@RequestParam(required = false) String gfclass, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false, value = "todoApp") boolean isTodo, @RequestParam(required = false, value = "wandererGame") boolean isWanderer, Model model) throws IOException {
        if (handlingError(gfclass, startDate, endDate, model)) return "commitchecker";
        List<String> ghHandles = ghCommitChecker.ghHandlesToString(classGithubRepo.findAllByClassName(gfclass));
        HashMap<String, List<Integer>> repoHashMap = ghCommitChecker.fillMapWithRepoRelevantStats(ghCommitChecker.checkRepos(ghHandles), startDate, endDate, ghCommitChecker.getGfLanguage(gfclass), isTodo, isWanderer);
        model.addAttribute("classes", classGithubRepo.getDistinctClasses());
        model.addAttribute("gfclass", gfclass);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("repoHashMap", repoHashMap);
        model.addAttribute("isTodo", isTodo);
        model.addAttribute("isWanderer", isWanderer);
        model.addAttribute("sums", ghCommitChecker.getTotalStats(repoHashMap, 3, isTodo, isWanderer));
        return "commitchecker";
    }

    private boolean handlingError(@RequestParam(required = false) String gfclass, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, Model model) {
        if (gfclass.equals("") || startDate.equals("") || endDate.equals("")) {
            model.addAttribute("error", "You are missing something, try again!");
            model.addAttribute("classes", classGithubRepo.getDistinctClasses());
            return true;
        }
        return false;
    }


}

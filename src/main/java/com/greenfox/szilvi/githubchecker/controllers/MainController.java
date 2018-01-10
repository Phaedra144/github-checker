package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.models.Repo;
import com.greenfox.szilvi.githubchecker.services.GHCommitChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    GHCommitChecker ghCommitChecker;

    @PostMapping("/commitcheck")
    public String checkCommits(@RequestParam String ghHandles, Model model) throws IOException {
        HashMap<String, Integer> notCommittedDays = new HashMap<>();
        List<String> classRepos = ghCommitChecker.getRepos(ghHandles);
        ghCommitChecker.fillNotCommittedDays(notCommittedDays, classRepos);
        model.addAttribute("map", notCommittedDays);
        return "index";
    }

    @GetMapping("/index")
    public String getMain(){
        return "index";
    }
}

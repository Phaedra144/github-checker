package com.greenfox.szilvi.githubchecker.commitcheck.web;

import com.greenfox.szilvi.githubchecker.commitcheck.service.CommitCheckService;
import com.greenfox.szilvi.githubchecker.githubhandles.persistance.dao.GithubHandleRepo;
import com.greenfox.szilvi.githubchecker.login.Authorization;
import com.greenfox.szilvi.githubchecker.login.CookieUtil;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.greenfox.szilvi.githubchecker.general.Settings.GITHUB_TOKEN;

@Controller
public class CommitCheckController {

    @Autowired
    CommitCheckService commitCheckService;

    @Autowired
    UserHandling userHandling;

    @GetMapping(value = {"", "/"})
    public String getMain(HttpServletRequest httpServletRequest) {
        return userHandling.checkTokenOnPage("index", httpServletRequest);
    }

    @GetMapping("/checkcommit")
    public String getCommitChecker(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("classes", commitCheckService.getDistinctClasses());
        return userHandling.checkTokenOnPage("commitchecker", httpServletRequest);
    }

    @PostMapping("/checkcommit")
    public String checkCommits(@RequestParam(required = false) String gfclass, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false, value = "todoApp") boolean isTodo, @RequestParam(required = false, value = "wandererGame") boolean isWanderer, HttpServletRequest request, Model model) throws IOException {

        if (handlingError(gfclass, startDate, endDate, model)) return "commitchecker";

        List<String> ghHandles = commitCheckService.ghHandlesToString(commitCheckService.findAllByClassName(gfclass));

        HashMap<String, List<Integer>> repoHashMap = commitCheckService.fillMapWithRepoRelevantStats("Bearer " + CookieUtil.getValue(request, GITHUB_TOKEN), commitCheckService.checkRepos(ghHandles), startDate, endDate, commitCheckService.getGfLanguage(gfclass), isTodo, isWanderer);

        model.addAttribute("classes", commitCheckService.getDistinctClasses());
        model.addAttribute("gfclass", gfclass);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("repoHashMap", repoHashMap);
        model.addAttribute("isTodo", isTodo);
        model.addAttribute("isWanderer", isWanderer);
        model.addAttribute("sums", commitCheckService.getTotalStats(repoHashMap, 3, isTodo, isWanderer));
        return "commitchecker";
    }

    private boolean handlingError(@RequestParam(required = false) String gfclass, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, Model model) {
        if (gfclass.equals("") || startDate.equals("") || endDate.equals("")) {
            model.addAttribute("error", "You are missing something, try again!");
            model.addAttribute("classes", commitCheckService.getDistinctClasses());
            return true;
        }
        return false;
    }


}

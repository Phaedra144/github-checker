package com.greenfox.szilvi.githubchecker.greenfoxteam.web;

import com.greenfox.szilvi.githubchecker.greenfoxteam.formvalid.GreenfoxTeamForm;
import com.greenfox.szilvi.githubchecker.greenfoxteam.service.GreenfoxDbService;
import com.greenfox.szilvi.githubchecker.greenfoxteam.service.GreenfoxTeamService;
import com.greenfox.szilvi.githubchecker.greenfoxteam.web.dto.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class GreenfoxTeamController {

    @Autowired
    UserHandling userHandling;

    @Autowired
    GreenfoxTeamService greenfoxTeamService;

    @Autowired
    GreenfoxDbService greenfoxDbService;

    @ModelAttribute
    public void addNewForm(Model model) {
        model.addAttribute("greenfoxTeamForm", new GreenfoxTeamForm());
    }

    @GetMapping("/addmembers")
    public String getMemberAdder(HttpServletRequest httpServletRequest) {
        return userHandling.checkTokenOnPage("members", httpServletRequest);
    }

    @PostMapping("/addmembers")
    public String addMember(@Valid GreenfoxTeamForm greenfoxTeamForm, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "members";
        }
        System.out.println(greenfoxTeamForm.isSaveToGithub());
        greenfoxDbService.saveToDb(greenfoxTeamForm);
        List<GreenfoxTeamStatus> memberStatusResponse = greenfoxTeamService.addNewMembersToGf(greenfoxTeamForm.getMembers(), greenfoxTeamForm.getCohortName(), greenfoxTeamForm.getClassName());
        model.addAttribute("responses", memberStatusResponse);
        return "members";
    }

    @GetMapping("/listmembers")
    public String listGhHandles(Model model) {
        model.addAttribute("members", greenfoxDbService.getAllHandles());
        return "members";
    }

    @RequestMapping(value = "/deletemembers/{id}")
    public String deleteGhHandles(@PathVariable long id) {
        greenfoxDbService.removeHandle(id);
        return "redirect:/listmembers";
    }

    @RequestMapping("/editmembers/{id}")
    public String editGhHandles(@PathVariable long id, String className, String cohortName, String githubHandle, String language) {
        greenfoxDbService.findAndReplace(id, className, cohortName, githubHandle, language);
        return "redirect:/listmembers";
    }
}

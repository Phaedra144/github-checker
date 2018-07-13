package com.greenfox.szilvi.githubchecker.greenfoxteam.web;

import com.greenfox.szilvi.githubchecker.greenfoxteam.formvalid.GreenfoxTeamForm;
import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.greenfoxteam.service.GreenfoxTeamService;
import com.greenfox.szilvi.githubchecker.login.Authorization;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/addgfamembers")
    public String getMemberAdder(GreenfoxTeamForm greenfoxTeamForm, HttpServletRequest httpServletRequest){
        return userHandling.checkTokenOnPage("memberadder", httpServletRequest);
    }

    @PostMapping("/addmembers")
    public String addMember(@Valid GreenfoxTeamForm greenfoxTeamForm, BindingResult bindingResult, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            return "memberadder";
        }
        List<GreenfoxTeamStatus> memberStatusResponse = greenfoxTeamService.addNewMembersToGf(greenfoxTeamForm.getMembers(), greenfoxTeamForm.getTeamName());
        model.addAttribute("responses", memberStatusResponse);
        return "memberadder";
    }
}

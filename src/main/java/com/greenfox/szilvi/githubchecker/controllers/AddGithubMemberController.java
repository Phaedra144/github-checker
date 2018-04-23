package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.entities.AddMemberForm;
import com.greenfox.szilvi.githubchecker.models.MemberStatusResponse;
import com.greenfox.szilvi.githubchecker.services.GhMemberService;
import com.greenfox.szilvi.githubchecker.services.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class AddGithubMemberController {

    @Autowired
    Authorization authorization;

    @Autowired
    GhMemberService GhMemberService;

    @GetMapping("/addgfamembers")
    public String getMemberAdder(AddMemberForm addMemberForm){
        return authorization.checkTokenOnPage("memberadder");
    }

    @PostMapping("/addmembers")
    public String addMember(@Valid AddMemberForm addMemberForm, BindingResult bindingResult, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            return "memberadder";
        }
        List<MemberStatusResponse> memberStatusResponse = GhMemberService.addNewMembersToGf(addMemberForm.getMembers(), addMemberForm.getTeamName());
        model.addAttribute("responses", memberStatusResponse);
        return "memberadder";
    }
}

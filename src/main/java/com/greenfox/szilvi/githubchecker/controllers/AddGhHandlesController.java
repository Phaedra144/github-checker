package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.services.GhHandleService;
import com.greenfox.szilvi.githubchecker.services.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddGhHandlesController {

    @Autowired
    Authorization authorization;

    @Autowired
    GhHandleService ghHandleService;

    @GetMapping("/addhandles")
    public String getGhHandleAdder(){
        return authorization.checkTokenOnPage("gh_handles");
    }

    @PostMapping("/addhandles")
    public String addMember(String cohortName, String className, String ghHdls, Model model) {
        if(cohortName.equals("") || className.equals("") || ghHdls.equals("")){
            return "gh_handles";
        }
        List<String> ghHandles = ghHandleService.handleListOfHandles(ghHdls);
        ghHandleService.saveGhHandlesToClass(ghHandles, cohortName, className);
        return "gh_handles";
    }

    @GetMapping("/listhandles")
    public String listGhHandles(Model model){
        model.addAttribute("handles", ghHandleService.getAllHandles());
        return "gh_handles";
    }

    @RequestMapping("/deletehandles/{id}")
    public String deleteGhHandles(@PathVariable long id){
        ghHandleService.removeHandle(id);
        return "redirect:/listhandles";
    }

    @RequestMapping("/edithandles/{id}")
    public String editGhHandles(@PathVariable long id, String className, String cohortName, String githubHandle){
        ghHandleService.findAndReplace(id, className, cohortName, githubHandle);
        return "redirect:/listhandles";
    }

}

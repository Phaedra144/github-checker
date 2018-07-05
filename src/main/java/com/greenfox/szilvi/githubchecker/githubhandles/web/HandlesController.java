package com.greenfox.szilvi.githubchecker.githubhandles.web;

import com.greenfox.szilvi.githubchecker.githubhandles.service.HandlesService;
import com.greenfox.szilvi.githubchecker.login.Authorization;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HandlesController {

    @Autowired
    UserHandling userHandling;

    @Autowired
    HandlesService handlesService;

    @GetMapping("/addhandles")
    public String getGhHandleAdder(){
        return userHandling.checkTokenOnPage("gh_handles");
    }

    @PostMapping("/addhandles")
    public String addMember(String cohortName, String className, String ghHdls) {
        if(cohortName.equals("") || className.equals("") || ghHdls.equals("")){
            return "gh_handles";
        }
        List<String> ghHandles = handlesService.handleListOfHandles(ghHdls);
        handlesService.saveGhHandlesToClass(ghHandles, cohortName, className);
        return "gh_handles";
    }

    @GetMapping("/listhandles")
    public String listGhHandles(Model model){
        model.addAttribute("handles", handlesService.getAllHandles());
        return "gh_handles";
    }

    @RequestMapping(value = "/deletehandles/{id}")
    public String deleteGhHandles(@PathVariable long id){
        handlesService.removeHandle(id);
        return "gh_handles";
    }

    @RequestMapping("/edithandles/{id}")
    public String editGhHandles(@PathVariable long id, String className, String cohortName, String githubHandle){
        handlesService.findAndReplace(id, className, cohortName, githubHandle);
        return "redirect:/listhandles";
    }

}

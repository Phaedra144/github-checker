package com.greenfox.szilvi.githubchecker.githubhandles.web;

import com.greenfox.szilvi.githubchecker.greenfoxteam.service.GreenfoxDbService;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HandlesController {

    @Autowired
    UserHandling userHandling;

    @Autowired
    GreenfoxDbService greenfoxDbService;

    @GetMapping("/addhandles")
    public String getGhHandleAdder(HttpServletRequest httpServletRequest){
        return userHandling.checkTokenOnPage("gh_handles", httpServletRequest);
    }

//    @PostMapping("/addhandles")
//    public String addMember(String cohortName, String className, String ghHdls) {
//        if(cohortName.equals("") || className.equals("") || ghHdls.equals("")){
//            return "gh_handles";
//        }
//        List<String> ghHandles = greenfoxDbService.handleListOfHandles(ghHdls);
//        greenfoxDbService.saveGhHandlesToClass(ghHandles, cohortName, className);
//        return "gh_handles";
//    }

    @GetMapping("/listhandles")
    public String listGhHandles(Model model){
        model.addAttribute("handles", greenfoxDbService.getAllHandles());
        return "gh_handles";
    }

    @RequestMapping(value = "/deletehandles/{id}")
    public String deleteGhHandles(@PathVariable long id){
        greenfoxDbService.removeHandle(id);
        return "gh_handles";
    }

    @RequestMapping("/edithandles/{id}")
    public String editGhHandles(@PathVariable long id, String className, String cohortName, String githubHandle){
        greenfoxDbService.findAndReplace(id, className, cohortName, githubHandle);
        return "redirect:/listhandles";
    }

}

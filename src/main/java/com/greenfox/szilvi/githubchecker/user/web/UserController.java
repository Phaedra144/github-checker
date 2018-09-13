package com.greenfox.szilvi.githubchecker.user.web;

import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.Auth;
import com.greenfox.szilvi.githubchecker.user.service.MentorMemberService;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;


@Controller
public class UserController {

    @Autowired
    UserHandling userHandling;

    @Autowired
    MentorMemberService mentorMemberService;

    @RequestMapping("/validate")
    public String saveUser(Model model, HttpServletResponse httpServletResponse) {
        Auth lastAuth = userHandling.findLastAuth();
        String accessToken = lastAuth.getAccessToken();
        System.out.println(accessToken);
        UserDTO recentUserDTO = userHandling.getAuthUser();
        if (mentorMemberService.checkIfUserMemberOfMentors(recentUserDTO, accessToken)) {
            userHandling.saveUser(recentUserDTO, lastAuth, httpServletResponse);
            return "redirect:/";
        } else {
            model.addAttribute("notMentor", "Oooops, sorry, but only mentors can access this app!");
            return "redirect:/logout";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        userHandling.logout(httpServletResponse);
        return "login";
    }
}

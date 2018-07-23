package com.greenfox.szilvi.githubchecker.user.web;

import com.greenfox.szilvi.githubchecker.login.CookieUtil;
import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.service.MentorMemberService;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.greenfox.szilvi.githubchecker.general.Settings.GITHUB_TOKEN;

@Controller
public class UserController {

    @Autowired
    UserHandling userHandling;

    @Autowired
    MentorMemberService mentorMemberService;

    @RequestMapping("/saveUser")
    public String saveUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        String accessToken = CookieUtil.getValue(request, GITHUB_TOKEN);
        System.out.println(accessToken);
        UserDTO recentUserDTO = userHandling.getAuthUser(accessToken);
        userHandling.saveNewUser(accessToken, recentUserDTO);
        if (mentorMemberService.checkIfUserMemberOfMentors(recentUserDTO, accessToken)) {
            return "redirect:/";
        } else {
            model.addAttribute("notMentor", "Oooops, sorry, but only mentors can access this app!");
            userHandling.logout(response);
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        userHandling.logout(httpServletResponse);
        return "login";
    }
}

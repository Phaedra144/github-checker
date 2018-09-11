
package com.greenfox.szilvi.githubchecker.user.web;

import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.service.MentorMemberService;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {

    @Autowired
    UserHandling userHandling;

    @Autowired
    MentorMemberService mentorMemberService;

    @RequestMapping("/validate")
    public String saveUser(Model model) {
        String accessToken = userHandling.findLastAuth().getAccessToken();
        System.out.println(accessToken);
        UserDTO recentUserDTO = userHandling.getAuthUser();
        userHandling.updateUser(recentUserDTO);
        if (mentorMemberService.checkIfUserMemberOfMentors(recentUserDTO, accessToken)) {
            return "redirect:/";
        } else {
            model.addAttribute("notMentor", "Oooops, sorry, but only mentors can access this app!");
            userHandling.logout();
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout() {
        userHandling.logout();
        return "login";
    }
}

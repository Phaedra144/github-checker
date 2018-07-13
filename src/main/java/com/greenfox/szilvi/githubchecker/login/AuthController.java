package com.greenfox.szilvi.githubchecker.login;

import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;


@Controller
public class AuthController {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Autowired
    Authorization authorization;

    @Autowired
    UserHandling userHandling;

    @RequestMapping("/login")
    public String renderLogin() {
        return "login";
    }

    @RequestMapping("/oauth")
    public String redirecttoOauth() {
        String url = "";
        if (IS_LOCALHOST.equals("localhost")) {
            url = LOCALHOST;
        } else if (IS_LOCALHOST.equals("aws")) {
            url = AWS;
        } else {
            url = HEROKU;
        }
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + url + "&scope=repo%20admin:org";
    }

    @RequestMapping("/auth")
    public String getAccessToken(@RequestParam String code, HttpServletResponse httpServletResponse, Model model) throws IOException {
        String accessToken = authorization.getAccessToken(code);
        CookieUtil.create(httpServletResponse, GITHUB_TOKEN, accessToken, false, 86400000, "localhost");

        System.out.println(accessToken);
        return "redirect:/saveUser";
    }

    @RequestMapping("/saveUser")
    public String saveUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        String accessToken = CookieUtil.getValue(request, GITHUB_TOKEN);
        System.out.println(accessToken);
        UserDTO recentUserDTO = userHandling.getAuthUser(accessToken);
        userHandling.saveNewUser(accessToken, recentUserDTO);
        if (userHandling.checkIfUserMemberOfMentors(recentUserDTO, accessToken)) {
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

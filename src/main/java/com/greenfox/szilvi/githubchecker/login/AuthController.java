package com.greenfox.szilvi.githubchecker.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;


@Controller
public class AuthController {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Autowired
    Authorization authorization;

    @RequestMapping("/login")
    public String renderLogin() {
        return "login";
    }

    @RequestMapping("/oauth")
    public String redirecttoOauth() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + AUTH_URL + "&scope=repo%20admin:org";
    }

    @RequestMapping("/auth")
    public String getAccessToken(@RequestParam String code, HttpServletResponse httpServletResponse) throws IOException {
        String accessToken = authorization.getAccessToken(code);
        CookieUtil.create(httpServletResponse, GITHUB_TOKEN, accessToken, false, ONE_DAY, COOKIE_DOMAIN);

        System.out.println(accessToken);
        return "redirect:/saveUser";
    }
}

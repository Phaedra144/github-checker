package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.services.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static com.greenfox.szilvi.githubchecker.services.Settings.*;
import java.io.IOException;


@Controller
public class AuthController {

    @Autowired
    Authorization authorization;

    @RequestMapping("/login")
    public String renderLogin(){
        return "login";
    }

    @RequestMapping("/oauth")
    public String redirecttoOauth(){
        String heroku = "https://github-checker.herokuapp.com/auth";
        String localhost = "http://localhost:8080/auth";
        String url = IS_LOCALHOST ? localhost : heroku;
        String clientId = System.getenv("CLIENT_ID");
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + url + "&scope=repo%20admin:org";
    }

    @RequestMapping("/auth")
    public String getAccessToken(@RequestParam String code) throws IOException {
        authorization.getAccessToken(code);
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(){
        System.setProperty(GITHUB_TOKEN, "");
        return "login";
    }
}

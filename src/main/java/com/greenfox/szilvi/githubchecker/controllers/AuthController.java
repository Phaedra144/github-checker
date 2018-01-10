package com.greenfox.szilvi.githubchecker.controllers;

import com.greenfox.szilvi.githubchecker.services.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "redirect:https://github.com/login/oauth/authorize?client_id=ea78181b0500c62004c9&redirect_uri=http://localhost:8080/auth&scope=repo";
    }

    @RequestMapping("/auth")
    public String getAccessToken(@RequestParam String code, Model model) throws IOException {
        authorization.getAccessToken(code);
        return "index";
    }
}

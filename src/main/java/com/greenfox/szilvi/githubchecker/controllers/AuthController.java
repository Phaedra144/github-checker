package com.greenfox.szilvi.githubchecker.controllers;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collections;


@Controller
public class AuthController {

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
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                httpTransport, jsonFactory,
                new GenericUrl("https://github.com/login/oauth/access_token"),
                new ClientParametersAuthentication(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET")),
                System.getenv("CLIENT_ID"),"https://github.com/login/oauth/authorize").build();
        TokenResponse tokenResponse = flow
                .newTokenRequest(code)
                .setScopes(Collections.singletonList("repo"))
                .setRequestInitializer(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) throws IOException {
                        request.getHeaders().setAccept("application/json");
                    }
                }).execute();
        model.addAttribute("token", tokenResponse.getAccessToken());
        return "index";
    }

}

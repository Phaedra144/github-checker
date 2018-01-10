package com.greenfox.szilvi.githubchecker.services;

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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class Authorization {

    public String getAccessToken(String code) throws IOException {
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
        System.setProperty("accessToken", tokenResponse.getAccessToken());
        return tokenResponse.getAccessToken();
    }
}

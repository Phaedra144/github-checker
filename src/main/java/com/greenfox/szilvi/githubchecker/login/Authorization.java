package com.greenfox.szilvi.githubchecker.login;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

import static com.greenfox.szilvi.githubchecker.general.Settings.GITHUB_TOKEN;

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
                .setScopes(Arrays.asList("repo", "admin:org"))
                .setRequestInitializer(httprequest -> {
                    httprequest.getHeaders().setAccept("application/json");}
                    ).execute();
        System.setProperty("accessToken", tokenResponse.getAccessToken());
        System.out.println(tokenResponse.getAccessToken());
        return tokenResponse.getAccessToken();
    }

    public String checkTokenOnPage(String whereTo) {
        if(System.getProperty(GITHUB_TOKEN) != ""){
            return whereTo;
        }else {
            return "login";
        }
    }
}

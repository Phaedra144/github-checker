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
import com.greenfox.szilvi.githubchecker.user.persistance.entity.User;
import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;

@Service
public class Authorization {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Autowired
    UserHandling userHandling;

    public String getAccessToken(String code) throws IOException {
        AuthorizationCodeFlow flow = getAuthorizationCodeFlow();
        TokenResponse tokenResponse = getTokenResponse(code, flow);
        return tokenResponse.getAccessToken();
    }

    private AuthorizationCodeFlow getAuthorizationCodeFlow() {
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();
        return new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                httpTransport, jsonFactory,
                new GenericUrl(GITHUB_OAUTH_ENCODED_URL),
                new ClientParametersAuthentication(clientId, clientSecret),
                clientId, GITHUB_OAUTH_AUTHORIZATION_URL).build();
    }

    private TokenResponse getTokenResponse(String code, AuthorizationCodeFlow flow) throws IOException {
        return flow
                .newTokenRequest(code)
                .setScopes(Arrays.asList("repo", "admin:org"))
                .setRequestInitializer(httprequest -> {
                            httprequest.getHeaders().setAccept("application/json");
                        }
                ).execute();
    }

}

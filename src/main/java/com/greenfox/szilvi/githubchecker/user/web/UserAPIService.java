package com.greenfox.szilvi.githubchecker.user.web;

import com.greenfox.szilvi.githubchecker.retrofit.GitHubRetrofit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAPIService {

    @Autowired
    GitHubRetrofit gitHubRetrofit;

    UserAPI userAPI;

    public UserAPI getUserAPI() {
        userAPI = gitHubRetrofit.getRetrofit().create(UserAPI.class);
        return userAPI;
    }
}

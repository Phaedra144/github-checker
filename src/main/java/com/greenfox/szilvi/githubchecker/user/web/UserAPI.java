package com.greenfox.szilvi.githubchecker.user.web;

import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserAPI {

    @GET("user")
    Call<UserDTO> getUser();
}

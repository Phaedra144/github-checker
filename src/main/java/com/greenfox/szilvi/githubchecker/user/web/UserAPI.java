package com.greenfox.szilvi.githubchecker.user.web;

import com.greenfox.szilvi.githubchecker.user.model.MentorMemberDTO;
import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface UserAPI {

    @GET("user")
    Call<UserDTO> getUser();

    @GET("teams/2114466/members")
    Call<List<MentorMemberDTO>> getMembersOfMentorsTeam();
}

package com.greenfox.szilvi.githubchecker.user.web;

import com.greenfox.szilvi.githubchecker.greenfoxteam.model.GreenfoxTeamStatus;
import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.persistance.dao.UserRepo;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class UserHandling {

    @Autowired
    UserAPIService userAPIService;

    @Autowired
    UserRepo userRepo;

    public UserDTO getAuthUser() throws IOException {
        Call<UserDTO> userDTOCall = userAPIService.getUserAPI().getUser();
        return userDTOCall.execute().body();
    }

    public void saveNewUser(String accessToken) throws IOException {
        UserDTO userDTO = getAuthUser();
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setAccessToken(accessToken);
        userRepo.save(user);
    }

}

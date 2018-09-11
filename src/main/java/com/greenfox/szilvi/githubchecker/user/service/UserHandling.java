package com.greenfox.szilvi.githubchecker.user.service;

import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.persistance.dao.AuthRepo;
import com.greenfox.szilvi.githubchecker.user.persistance.dao.UserRepo;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.Auth;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.User;
import com.greenfox.szilvi.githubchecker.user.web.UserAPIService;
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

    @Autowired
    AuthRepo authRepo;

    String TOKEN = findLastAuth().getAccessToken();

    public Auth findLastAuth() {
        try {
            return authRepo.findLastAuth();
        } catch (NullPointerException ex) {
            return new Auth();
        }
    }

    public UserDTO getAuthUser() {
        UserDTO userDTO = new UserDTO();
        try {
            Call<UserDTO> userDTOCall = userAPIService.getUserAPI().getUser(TOKEN);
            userDTO = userDTOCall.execute().body();
        } catch (IOException ex) {
            System.out.println("Something went wrong when querying user!");
        }
        return userDTO;
    }

    public void saveNewAuthWithAccessTokenOnly(String accessToken) {
        authRepo.save(new Auth(accessToken));
        TOKEN = "token " + accessToken;
    }

    public void updateUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setAccessToken(TOKEN);
        userRepo.save(user);
    }

    public String checkTokenOnPage(String whereTo) {
        if (TOKEN != null && checkIfUserIsValid()) {
            return whereTo;
        } else {
            return "login";
        }
    }

    public boolean checkIfUserIsValid() {
        User user = getUserByToken();
        return user.getLogin().equals(getAuthUser().getLogin());
    }

    public User getUserByToken() {
        return userRepo.findByAccessToken(TOKEN);
    }

    public void logout() {
        TOKEN = "";
    }
}

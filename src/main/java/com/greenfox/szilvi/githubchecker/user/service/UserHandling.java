package com.greenfox.szilvi.githubchecker.user.service;

import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.persistance.dao.UserRepo;
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

    String TOKEN = findLastUser().getAccessToken();

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

    public void saveNewUser(UserDTO recentUserDTO) {
        User user = userDTOtoNewUser(recentUserDTO);
        userRepo.save(user);
    }

    public User userDTOtoNewUser(UserDTO userDTO) {
        User user = findLastUser();
        userRepo.delete(user);
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setAccessToken(TOKEN);
        return user;
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

    public void saveNewUserWithAccessTokenOnly(String accessToken) {
        userRepo.save(new User(accessToken));
        TOKEN = "token " + accessToken;
    }

    public User findLastUser() {
        try {
            return userRepo.findLastUser();
        } catch (NullPointerException ex) {
            return new User();
        }
    }

    public void logout() {
        TOKEN = "";
    }
}

package com.greenfox.szilvi.githubchecker.user.web;

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

    public UserDTO getAuthUser() {
        UserDTO userDTO = new UserDTO();
        try {
            Call<UserDTO> userDTOCall = userAPIService.getUserAPI().getUser();
            userDTO = userDTOCall.execute().body();
        } catch (IOException ex) {
            System.out.println("Something went wrong when querying user!");
        }
        return userDTO;
    }

    public void saveNewUser(String accessToken) {
        User user = userDTOtoNewUser();
        user.setAccessToken(accessToken);
        userRepo.save(user);
    }

    public User userDTOtoNewUser() {
        UserDTO userDTO = getAuthUser();
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        return user;
    }

    public User getUserByToken(String token) {
        return userRepo.findByAccessToken(token);
    }

}

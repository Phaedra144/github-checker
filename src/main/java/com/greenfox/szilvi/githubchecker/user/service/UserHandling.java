package com.greenfox.szilvi.githubchecker.user.service;

import com.greenfox.szilvi.githubchecker.general.CookieUtil;
import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.persistance.dao.AuthRepo;
import com.greenfox.szilvi.githubchecker.user.persistance.dao.UserRepo;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.Auth;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.User;
import com.greenfox.szilvi.githubchecker.user.web.UserAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.greenfox.szilvi.githubchecker.general.Settings.COOKIE_DOMAIN;
import static com.greenfox.szilvi.githubchecker.general.Settings.ONE_DAY;
import static com.greenfox.szilvi.githubchecker.general.Settings.USER_SESSION;

@Service
public class UserHandling {

    @Autowired
    UserAPIService userAPIService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthRepo authRepo;

    String TOKEN = "";

    public Auth findLastAuth() {
        try {
            return authRepo.findLastAuth();
        } catch (NullPointerException ex) {
            return new Auth();
        }
    }

    public User findLastUser() {
        try {
            return userRepo.findLastUser();
        } catch (NullPointerException ex) {
            return new User();
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

    public void saveUser(UserDTO userDTO, Auth auth, HttpServletResponse httpServletResponse) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setAccessToken(TOKEN);
        user.setAuth(auth);
        userRepo.save(user);
        CookieUtil.create(httpServletResponse,  USER_SESSION, String.valueOf(user.getId()), false, ONE_DAY, COOKIE_DOMAIN);
    }

    public String checkTokenOnPage(String whereTo, HttpServletRequest httpServletRequest) {
          User user = getUserById(httpServletRequest);
        if (user != null && checkIfUserIsValid(user)) {
            return whereTo;
        } else {
            return "login";
        }
    }

    private boolean checkIfUserIsValid(User user) {
        return user.getLogin().equals(getAuthUser().getLogin());
    }

    public User getUserById(HttpServletRequest httpServletRequest) {
        Optional<String> userId = Optional.ofNullable(CookieUtil.getValue(httpServletRequest, USER_SESSION));
        if(userId.isPresent()){
            return userRepo.findById(Long.parseLong(userId.get()));
        }
        return null;
    }

    public void logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, USER_SESSION, COOKIE_DOMAIN);
    }
}

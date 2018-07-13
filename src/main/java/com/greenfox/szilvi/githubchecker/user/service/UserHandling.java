package com.greenfox.szilvi.githubchecker.user.service;

import com.greenfox.szilvi.githubchecker.login.CookieUtil;
import com.greenfox.szilvi.githubchecker.user.model.MentorMemberDTO;
import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.persistance.dao.UserRepo;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.User;
import com.greenfox.szilvi.githubchecker.user.web.UserAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.greenfox.szilvi.githubchecker.general.Settings.GITHUB_TOKEN;

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

    public String checkTokenOnPage(String whereTo, HttpServletRequest httpServletRequest) {
        if (CookieUtil.getValue(httpServletRequest, GITHUB_TOKEN).equals("") && checkIfUserIsValid(httpServletRequest)) {
            return whereTo;
        } else {
            return "login";
        }
    }

    public boolean checkIfUserIsValid(HttpServletRequest httpServletRequest) {
        String token = CookieUtil.getValue(httpServletRequest, GITHUB_TOKEN);
        User user = getUserByToken(token);
        return user.getLogin().equals(getAuthUser().getLogin());
    }

    public User getUserByToken(String token) {
        return userRepo.findByAccessToken(token);
    }

    public void logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, GITHUB_TOKEN);
    }

    public List<MentorMemberDTO> getMentors() {
        List<MentorMemberDTO> mentorMemberDTOList = new ArrayList<>();
        try {
            Call<List<MentorMemberDTO>> memberDTOcall = userAPIService.getUserAPI().getMembersOfMentorsTeam();
            mentorMemberDTOList = memberDTOcall.execute().body();
        } catch (IOException ex) {
            System.out.println("Something went wrong when querying user!");
        }
        return mentorMemberDTOList;
    }

    public boolean checkIfUserMemberOfMentors(UserDTO user) {
        List<MentorMemberDTO> mentors = getMentors();
        for (MentorMemberDTO mentor : mentors) {
            if (user.getLogin().equals(mentor.getLogin())){
                return true;
            }
        }
        return false;
    }

}

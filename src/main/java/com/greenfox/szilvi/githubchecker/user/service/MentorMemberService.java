package com.greenfox.szilvi.githubchecker.user.service;
import com.greenfox.szilvi.githubchecker.user.model.MentorMemberDTO;
import com.greenfox.szilvi.githubchecker.user.model.UserDTO;
import com.greenfox.szilvi.githubchecker.user.web.UserAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class MentorMemberService {

    @Autowired
    UserAPIService userAPIService;

    public boolean checkIfUserMemberOfMentors(UserDTO user, String token) {
        List<MentorMemberDTO> mentors = getMentors(token);
        for (MentorMemberDTO mentor : mentors) {
            if (user.getLogin().equals(mentor.getLogin())){
                return true;
            }
        }
        return false;
    }

    public List<MentorMemberDTO> getMentors(String token) {
        List<MentorMemberDTO> mentorMemberDTOList = new ArrayList<>();
        try {
            Call<List<MentorMemberDTO>> memberDTOcall = userAPIService.getUserAPI().getMembersOfMentorsTeam("token " + token);
            mentorMemberDTOList = memberDTOcall.execute().body();
        } catch (IOException ex) {
            System.out.println("Something went wrong when querying user!");
        }
        return mentorMemberDTOList;
    }
}

import java.io.IOException;
import java.util.*;
import models.Repo;
import services.AddGHMembers;
import services.GHCommitChecker;

import static services.Settings.*;


/**
 * Created by Szilvi on 2017. 09. 20..
 */
public class GitHubApp {

    public static void main(String[] args) {
        GHCommitChecker ghCommitChecker = new GHCommitChecker();
        AddGHMembers addGHMembers = new AddGHMembers();
        HashMap<String, Integer> notCommittedDays = new HashMap<>();
        try {
            if (IS_COMMITCHECKING){
                List<Repo> classRepos = ghCommitChecker.getRepos();
                ghCommitChecker.fillNotCommittedDays(notCommittedDays, classRepos);
            }
            if (IS_MEMBER_ADDING){
                addGHMembers.addNewMembersToGf(FILENAME_OF_MEMBERS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        printNotCommittedDays(notCommittedDays, 0);
    }

    private static void printNotCommittedDays(HashMap<String, Integer> notCommittedDays, int total) {
        for (Map.Entry entry : notCommittedDays.entrySet()) {
            total = total + (int) entry.getValue();
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        System.out.println("Total: " + total);
    }
}

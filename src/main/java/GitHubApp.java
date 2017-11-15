import java.io.IOException;
import java.util.*;
import models.Repo;
import services.GitHubService;


/**
 * Created by Szilvi on 2017. 09. 20..
 */
public class GitHubApp {

    static  boolean IS_COMMITCHECKING = false;
    static boolean IS_MEMBER_ADDING = true;
    static String FILENAME_OF_MEMBERS = "C:\\Users\\Szilvi\\Downloads\\2017-11-Corsac cohort Github usernames - Sheet1.csv";

    public static void main(String[] args) {
        GitHubService gitHubService = new GitHubService();
        HashMap<String, Integer> notCommittedDays = new HashMap<>();
        try {
            if (IS_COMMITCHECKING){
                List<Repo> classRepos = gitHubService.getRepos();
                gitHubService.fillNotCommittedDays(notCommittedDays, classRepos);
            }
            if (IS_MEMBER_ADDING){
                gitHubService.addNewMembersToGf(FILENAME_OF_MEMBERS);
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

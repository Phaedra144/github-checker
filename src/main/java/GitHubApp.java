import java.io.IOException;
import java.util.*;
import models.Repo;
import services.GitHubService;


/**
 * Created by Szilvi on 2017. 09. 20..
 */
public class GitHubApp {

    public static void main(String[] args) {
        GitHubService gitHubService = new GitHubService();
        HashMap<String, Integer> notCommittedDays = new HashMap<>();
        try {
            List<Repo> classRepos = gitHubService.getRepos();
            gitHubService.fillNotCommittedDays(notCommittedDays, classRepos);
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

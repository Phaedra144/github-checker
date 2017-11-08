import models.Repo;
import services.GitHubService;

import java.io.IOException;
import java.util.*;

/**
 * Created by Szilvi on 2017. 09. 20..
 */
public class GitHubApp {
    /** Ez csak nekem nem egyértelmű hogy ez miért IOExceptiont dob. Van erre valami hogy ennek mindig azt kell dobnia? 
     *  Csak mert direktbe nincs IO művelet benne.
     */
    public static void main(String[] args) throws IOException {
        GitHubService gitHubService = new GitHubService();
        HashMap<String, Integer> notCommittedDays = new HashMap<>();
        List<Repo> classRepos = gitHubService.getRepos(); // hiányzik egy space :D
        int total = 0; //  nah ez az ami nem tiszta, szerintem itt felesleges a változó deklarálás. Csak simán adj át 0-t a fügvénynek
        gitHubService.fillNotCommittedDays(notCommittedDays, classRepos);
        printNotCommittedDays(notCommittedDays, total);
    }

    private static void printNotCommittedDays(HashMap<String, Integer> notCommittedDays, int total) {
        for (Map.Entry entry : notCommittedDays.entrySet()) {
            total = total + (int)entry.getValue();
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        System.out.println("Total: " + total);
    }
}

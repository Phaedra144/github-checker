package models;


/**
 * Created by Szilvi on 2017. 09. 30..
 */
public class GfCommits {

    private String sha;
    private Commit commit;

    public Commit getCommit() {
        return commit;
    }

    @Override
    public String toString() {
        return "models.GfCommits{" +
                "sha='" + sha + '\'' +
                ", commit=" + commit +
                '}';
    }
}

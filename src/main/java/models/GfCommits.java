package models;


/**
 * Created by Szilvi on 2017. 09. 30..
 */
public class GfCommits {

    String sha;
    Commit commit;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return "models.GfCommits{" +
                "sha='" + sha + '\'' +
                ", commit=" + commit +
                '}';
    }
}

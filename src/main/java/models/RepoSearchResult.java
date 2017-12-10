package models;

import lombok.Getter;

import java.util.List;

public class RepoSearchResult {

    @Getter
    private List<Repo> items;

    @Override
    public String toString() {
        return "models.RepoSearchResult{" +
                "items=" + items +
                '}';
    }
}

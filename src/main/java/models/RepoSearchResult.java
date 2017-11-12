package models;

import models.Repo;

import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
public class RepoSearchResult {

    private List<Repo> items;

    public List<Repo> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "models.RepoSearchResult{" +
                "items=" + items +
                '}';
    }
}

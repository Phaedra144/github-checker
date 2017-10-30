package models;

import models.Repo;

import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
public class RepoSearchResult {

    List<Repo> items;

    public void setItems(List<Repo> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "models.RepoSearchResult{" +
                "items=" + items +
                '}';
    }

    public List<Repo> getItems() {
        return items;
    }
}

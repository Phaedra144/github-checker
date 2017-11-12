package models;

import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
public class Repo {

    private int id;
    private String name;

    public Repo(int id, String name, List<String> topics) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "models.Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
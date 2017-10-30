package models;

import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
public class Repo {

    int id;
    String name;

    public Repo(int id, String name, List<String> topics) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "models.Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
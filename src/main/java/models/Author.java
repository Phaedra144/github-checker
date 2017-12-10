package models;

import lombok.Getter;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class Author {

    private String name;
    @Getter
    private String date;

    @Override
    public String toString() {
        return "models.Author{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

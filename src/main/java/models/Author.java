package models;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class Author {

    private String name;
    private String date;

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "models.Author{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

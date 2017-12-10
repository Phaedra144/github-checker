package models;

import lombok.Getter;

/**
 * Created by Szilvi on 2017. 10. 04..
 */

public class Commit {

    @Getter
    private Author author;
    private String message;

    @Override
    public String toString() {
        return "models.Commit{" +
                "author=" + author +
                ", message='" + message + '\'' +
                '}';
    }
}

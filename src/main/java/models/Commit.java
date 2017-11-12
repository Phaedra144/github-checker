package models;

import models.Author;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class Commit {

    private Author author;
    private String message;

    public Author getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "models.Commit{" +
                "author=" + author +
                ", message='" + message + '\'' +
                '}';
    }
}

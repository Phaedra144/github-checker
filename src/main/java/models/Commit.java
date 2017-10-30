package models;

import models.Author;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class Commit {

    Author author;
    String message;

    public Author getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "models.Commit{" +
                "author=" + author +
                ", message='" + message + '\'' +
                '}';
    }
}

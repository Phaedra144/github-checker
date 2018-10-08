package com.greenfox.szilvi.githubchecker.greenfoxteam.persistance.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
@Setter
@Getter
@Entity
public class ClassGithub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String cohortName;
    String className;
    String githubHandle;
    String language;

    public ClassGithub() {
    }

    public ClassGithub(String cohortName, String className, String githubHandle, String language) {
        this.cohortName = cohortName;
        this.className = className;
        this.githubHandle = githubHandle;
        this.language = language;
    }
}

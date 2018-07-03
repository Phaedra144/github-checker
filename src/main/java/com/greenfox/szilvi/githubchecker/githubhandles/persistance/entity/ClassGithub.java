package com.greenfox.szilvi.githubchecker.githubhandles.persistance.entity;
import lombok.Setter;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
@Setter
@Entity
public class ClassGithub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String cohortName;
    String className;
    String githubHandle;

    public ClassGithub() {
    }

    public ClassGithub(String cohortName, String className, String githubHandle) {
        this.cohortName = cohortName;
        this.className = className;
        this.githubHandle = githubHandle;
    }

    public long getId() {
        return id;
    }

    public String getCohortName() {
        return cohortName;
    }

    public String getClassName() {
        return className;
    }

    public String getGithubHandle() {
        return githubHandle;
    }

}
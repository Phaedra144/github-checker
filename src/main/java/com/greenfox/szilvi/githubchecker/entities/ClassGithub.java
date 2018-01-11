package com.greenfox.szilvi.githubchecker.entities;

import lombok.Getter;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Szilvi on 2017. 10. 03..
 */

@Entity
public class ClassGithub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
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
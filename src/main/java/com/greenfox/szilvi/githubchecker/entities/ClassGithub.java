package com.greenfox.szilvi.githubchecker.entities;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
@Getter
@Entity
public class ClassGithub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String cohortName;
    private String className;
    private List<String> githubHandles;

    public ClassGithub(String cohortName, String className, List<String> githubHandles) {
        this.cohortName = cohortName;
        this.className = className;
        this.githubHandles = githubHandles;
    }

}
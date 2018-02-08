package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.entities.ClassGithub;
import com.greenfox.szilvi.githubchecker.repositories.GithubHandleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GhHandleService {

    @Autowired
    GithubHandleRepo githubHandleRepo;

    public List<String> handleListOfHandles(String ghHandles){
        String splitter = "";
        for(char strChar:ghHandles.toCharArray()){
            if(!splitter.contains(" ") && !splitter.contains("\r") || !splitter.contains("\n")){
                if (strChar == ' ' || strChar == '\r' || strChar == '\n'){
                    splitter = splitter + strChar;
                }
            }
        }
        return new ArrayList<String>(Arrays.asList(ghHandles.split(splitter)));
    }


    public void saveGhHandlesToClass(List<String> ghHandles, String cohortName, String className) {
        for (String ghHandle:ghHandles) {
            githubHandleRepo.save(new ClassGithub(cohortName, className, ghHandle));
        }
    }

    public List<ClassGithub> getAllHandles() {
        return githubHandleRepo.findAll();
    }

    public void removeHandle(long id) {
        githubHandleRepo.delete(id);
    }

    public void findAndReplace(long id, String className, String cohortName, String githubHandle) {
        ClassGithub classGithub = githubHandleRepo.findOne(id);
        classGithub.setClassName(className);
        classGithub.setCohortName(cohortName);
        classGithub.setGithubHandle(githubHandle);
        githubHandleRepo.save(classGithub);
    }
}

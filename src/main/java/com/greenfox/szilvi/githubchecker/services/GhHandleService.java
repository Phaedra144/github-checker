package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.entities.ClassGithub;
import com.greenfox.szilvi.githubchecker.repositories.GithubHandleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GhHandleService {

    @Autowired
    GithubHandleRepo githubHandleRepo;


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
}

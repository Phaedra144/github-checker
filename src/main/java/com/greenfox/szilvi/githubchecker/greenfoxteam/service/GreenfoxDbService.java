package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import com.greenfox.szilvi.githubchecker.greenfoxteam.formvalid.GreenfoxTeamForm;
import com.greenfox.szilvi.githubchecker.greenfoxteam.persistance.entity.ClassGithub;
import com.greenfox.szilvi.githubchecker.greenfoxteam.persistance.dao.GithubHandleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GreenfoxDbService {

    @Autowired
    GithubHandleRepo githubHandleRepo;

    @Autowired
    GreenfoxTeamService greenfoxTeamService;


    public void saveGhHandlesToClass(List<String> ghHandles, String cohortName, String className) {
        for (String ghHandle:ghHandles) {
            githubHandleRepo.save(new ClassGithub(cohortName, className, ghHandle));
        }
    }

    public void saveToDb(@Valid GreenfoxTeamForm greenfoxTeamForm) {
        List<String> ghHandles = greenfoxTeamService.handleListOfHandles(greenfoxTeamForm.getMembers());
        saveGhHandlesToClass(ghHandles, greenfoxTeamForm.getCohortName(), greenfoxTeamForm.getClassName());
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

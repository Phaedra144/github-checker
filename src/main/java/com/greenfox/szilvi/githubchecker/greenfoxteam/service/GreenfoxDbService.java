package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import com.greenfox.szilvi.githubchecker.greenfoxteam.formvalid.GreenfoxTeamForm;
import com.greenfox.szilvi.githubchecker.greenfoxteam.persistance.dao.GithubHandleRepo;
import com.greenfox.szilvi.githubchecker.greenfoxteam.persistance.entity.ClassGithub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class GreenfoxDbService {

    @Autowired
    GithubHandleRepo githubHandleRepo;

    @Autowired
    GithubHandleParser githubHandleParser;

    public void saveToDb(@Valid GreenfoxTeamForm greenfoxTeamForm) {
        List<String> ghHandles = githubHandleParser.handleListOfHandles(greenfoxTeamForm.getMembers());
        saveGhHandlesToClass(ghHandles, greenfoxTeamForm.getCohortName(), greenfoxTeamForm.getClassName(), githubHandleParser.parseLanguage(greenfoxTeamForm.getLanguage()));
    }

    public void saveGhHandlesToClass(List<String> ghHandles, String cohortName, String className, String language) {
        for (String ghHandle : ghHandles) {
            if (ghHandleIsNotInDb(ghHandle)) {
                githubHandleRepo.save(new ClassGithub(cohortName, className, ghHandle, language));
            }
            else {
                replaceClassGithub(className, cohortName, ghHandle, language, githubHandleRepo.findByGithubHandle(ghHandle));
            }
        }
    }

    private boolean ghHandleIsNotInDb(String ghHandle) {
        return githubHandleRepo.findByGithubHandle(ghHandle) == null;
    }

    public List<ClassGithub> getAllHandles() {
        return githubHandleRepo.findAll();
    }

    public void removeHandle(long id) {
        githubHandleRepo.delete(id);
    }

    public void findAndReplace(long id, String className, String cohortName, String githubHandle, String language) {
        ClassGithub classGithub = githubHandleRepo.findOne(id);
        replaceClassGithub(className, cohortName, githubHandle, language, classGithub);
        githubHandleRepo.save(classGithub);
    }

    private void replaceClassGithub(String className, String cohortName, String githubHandle, String language, ClassGithub classGithub) {
        classGithub.setClassName(className);
        classGithub.setCohortName(cohortName);
        classGithub.setGithubHandle(githubHandle);
        classGithub.setLanguage(language);
    }

    public List<ClassGithub> findAllByClassName(String gfclass) {
        return githubHandleRepo.findAllByClassName(gfclass);
    }

    public List<String> getDistinctClasses() {
        return githubHandleRepo.getDistinctClasses();
    }

    public ClassGithub getStudentFromDB(String ghHandle) {
        return githubHandleRepo.findByGithubHandle(ghHandle);
    }

}

package com.greenfox.szilvi.githubchecker.githubhandles.persistance.dao;

import com.greenfox.szilvi.githubchecker.githubhandles.persistance.entity.ClassGithub;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GithubHandleRepo extends CrudRepository<ClassGithub, Long> {

    @Query(value = "SELECT DISTINCT class_name FROM class_github", nativeQuery = true)
    List<String> getDistinctClasses();

    List<ClassGithub> findAllByClassName(String className);

    @Query(value = "SELECT * FROM class_github ORDER BY cohort_name ASC, class_name ASC", nativeQuery = true)
    List<ClassGithub>findAll();

    ClassGithub findByGithubHandle(String githubHandle);

}

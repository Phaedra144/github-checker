package com.greenfox.szilvi.githubchecker.repositories;

import com.greenfox.szilvi.githubchecker.entities.ClassGithub;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClassGithubRepo extends CrudRepository<ClassGithub, Long> {

    @Query(value = "SELECT DISTINCT class_name FROM class_github", nativeQuery = true)
    List<String> getDistinctClasses();

    List<ClassGithub> findAllByClassName(String className);
}

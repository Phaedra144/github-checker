package com.greenfox.szilvi.githubchecker.user.persistance.dao;

import com.greenfox.szilvi.githubchecker.user.persistance.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByAccessToken(String accessToken);
    User findById(long id);
}

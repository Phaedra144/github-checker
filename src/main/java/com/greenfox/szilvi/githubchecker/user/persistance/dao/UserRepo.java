package com.greenfox.szilvi.githubchecker.user.persistance.dao;

import com.greenfox.szilvi.githubchecker.user.persistance.entity.Auth;
import com.greenfox.szilvi.githubchecker.user.persistance.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByAccessToken(String accessToken);
    User findById(long id);

    @Query(value = "SELECT * FROM app_user ORDER BY id DESC LIMIT 1", nativeQuery = true)
    User findLastUser();
}

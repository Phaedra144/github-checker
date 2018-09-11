package com.greenfox.szilvi.githubchecker.user.persistance.dao;

import com.greenfox.szilvi.githubchecker.user.persistance.entity.Auth;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthRepo extends CrudRepository<Auth, Long> {

    Auth findByAccessToken(String accessToken);

    @Query(value = "SELECT * FROM auth ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Auth findLastAuth();

}

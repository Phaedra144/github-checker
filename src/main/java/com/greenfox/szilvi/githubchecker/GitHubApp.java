package com.greenfox.szilvi.githubchecker;

import java.io.IOException;
import java.util.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.greenfox.szilvi.githubchecker.services.AddGHMembers;
import com.greenfox.szilvi.githubchecker.services.GHCommitChecker;

import static com.greenfox.szilvi.githubchecker.services.Settings.*;


/**
 * Created by Szilvi on 2017. 09. 20..
 */
@SpringBootApplication
public class GitHubApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GitHubApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}

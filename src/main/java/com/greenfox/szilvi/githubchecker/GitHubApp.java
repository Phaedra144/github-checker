package com.greenfox.szilvi.githubchecker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;


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
        System.setProperty(GITHUB_TOKEN, "");
    }
}

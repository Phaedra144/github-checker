package com.greenfox.szilvi.githubchecker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.http.Cookie;

import java.util.Scanner;

import static com.greenfox.szilvi.githubchecker.general.Settings.*;

/**
 * Created by Szilvi on 2017. 09. 20..
 */

@SpringBootApplication
@EnableAutoConfiguration
public class GitHubApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GitHubApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}




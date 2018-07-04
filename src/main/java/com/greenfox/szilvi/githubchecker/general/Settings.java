package com.greenfox.szilvi.githubchecker.general;

public class Settings {

    public static String IS_LOCALHOST = System.getenv("IS_LOCALHOST");
    public static String GITHUB_TOKEN = "accessToken";
    public static String GITHUB_ORG = "green-fox-academy";
    public static int MENTORS_TEAM_ID = 2114466;
    public static String TODO_APP = "todo-app";
    public static String WANDERER = "wanderer-";
    public static String HEROKU = "http://githubchecker-env.w2bduppry4.eu-west-1.elasticbeanstalk.com/auth";
    public static String LOCALHOST = "http://localhost:8080/auth";
    public static String GITHUB_OAUTH_ENCODED_URL = "https://github.com/login/oauth/access_token";
    public static String GITHUB_OAUTH_AUTHORIZATION_URL = "https://github.com/login/oauth/authorize";
}

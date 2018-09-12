package com.greenfox.szilvi.githubchecker.general;

public class Settings {

    public static String AUTH_URL = System.getenv("AUTH_URL");
    public static String USER_SESSION = "UserID";
    public static String GITHUB_ORG = "green-fox-academy";
    public static int MENTORS_TEAM_ID = 2114466;
    public static String TODO_APP = "todo-app";
    public static String WANDERER = "wanderer-";
    public static String GITHUB_OAUTH_ENCODED_URL = "https://github.com/login/oauth/access_token";
    public static String GITHUB_OAUTH_AUTHORIZATION_URL = "https://github.com/login/oauth/authorize";
    public static int ONE_DAY = 24 * 60 * 60 * 1000;
    public static String COOKIE_DOMAIN = System.getenv("COOKIE_DOMAIN");
}

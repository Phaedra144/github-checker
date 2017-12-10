package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Settings {

    public static String GITHUB_SYSTEM_ENV = "COMMIT_CHECKER";
    public static  boolean IS_COMMITCHECKING = true;
    public static boolean IS_MEMBER_ADDING = false;
    public static String FILENAME_OF_MEMBERS = "C:\\Users\\Szilvi\\Downloads\\2017-11-Corsac cohort Github usernames - Sheet1.csv";
    public static String GITHUB_ORG = "greenfox-academy";
    public static int COHORT_ID = 2555784;
    public static List<String> EXCLUDE_REPOS = new ArrayList<>(Arrays.asList("todo", "RPG", "to-do"));

}

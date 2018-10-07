package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GithubHandleParser {

    public List<String> checkRepos(List<String> ghHandles) {
        if (ghHandles.get(0).substring(0, 2).equals(",,")) {
            String firstGhHandle = cutFirstChar(ghHandles.get(0));
            ghHandles.remove(0);
            ghHandles.add(0, firstGhHandle);
        }
        return ghHandles;
    }

    private String cutFirstChar(String firstGhHandle) {
        return firstGhHandle.substring(2);
    }

    public List<String> handleListOfHandles(String ghHandles) {
        String splitter = "";
        ArrayList<String> theHandles = new ArrayList<>();
        for (char strChar : ghHandles.toCharArray()) {
            if (!splitter.contains(" ")) {
                if (!splitter.contains("\r") || !splitter.contains("\n")) {
                    if (strChar == ' ' || strChar == '\r' || strChar == '\n') {
                        splitter = splitter + strChar;
                        theHandles = new ArrayList<String>(Arrays.asList(ghHandles.split(splitter)));
                    }
                }
            }
        }
        return theHandles.size() > 1 ? theHandles : new ArrayList<>(Arrays.asList(ghHandles));
    }

    public String parseLanguage(String language) {
        if (language.equalsIgnoreCase("cs") || language.equalsIgnoreCase("cpp")) {
            String transformed = "";
            switch (language.toLowerCase()) {
                case "cs":
                    transformed = "C#";
                case "cpp":
                    transformed = "C++";
            }
            return transformed;
        }
        return language;
    }

    public String getRepoType(String repoType, String language) {
        String transformedLanguage = "";
        if (repoType.equals("wanderer-")) {
            switch (language.toLowerCase()) {
                case "java":
                    transformedLanguage = "java";
                    break;
                case "c#":
                    transformedLanguage = "cs";
                    break;
                case "typescript":
                    transformedLanguage = "typescript";
            }
            return repoType + transformedLanguage;
        }
        return repoType;
    }
}

package com.greenfox.szilvi.githubchecker.greenfoxteam.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GithubHandleParser {

    public List<String> handleListOfHandles(String ghHandles){
        String splitter = "";
        ArrayList<String> theHandles = new ArrayList<>();
        for(char strChar:ghHandles.toCharArray()){
            if(!splitter.contains(" ")){
                if(!splitter.contains("\r") || !splitter.contains("\n")){
                    if (strChar == ' ' || strChar == '\r' || strChar == '\n'){
                        splitter = splitter + strChar;
                        theHandles = new ArrayList<String>(Arrays.asList(ghHandles.split(splitter)));
                    }
                }
            }
        }
        return theHandles.size() > 1 ? theHandles : new ArrayList<>(Arrays.asList(ghHandles));
    }
}

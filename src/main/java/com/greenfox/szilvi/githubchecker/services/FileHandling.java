package com.greenfox.szilvi.githubchecker.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {

    public List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        try { // Required by Files.readAllLines(filePath);
            Path filePath = Paths.get(filename);
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Uh-oh, could not read the file!");
        }
        return lines;
    }

    public String trunkTheLastPart(String longGhHandle){
        String[] myArray = longGhHandle.split(",");
        String realHandle = null;
        if (myArray.length > 2){
            realHandle = myArray[myArray.length-1];
        }
        else {
            realHandle = "";
        }
        return realHandle;
    }
}

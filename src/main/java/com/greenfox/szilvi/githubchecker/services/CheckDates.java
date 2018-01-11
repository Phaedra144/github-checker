package com.greenfox.szilvi.githubchecker.services;

import com.greenfox.szilvi.githubchecker.models.GfCommits;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class CheckDates {

    public int checkHowManyDaysNotCommitted(List<GfCommits> gfCommits, String startDate, String endDate) {
        int count = 0;
        HashMap<LocalDate, Integer> myMap = daysOfNotCommiting(gfCommits, startDate, endDate);
        for (Map.Entry entry : myMap.entrySet()) {
            if (entry.getValue().equals(0)){
                count++;
            }
        }
        return count;
    }

    public HashMap<LocalDate, Integer> daysOfNotCommiting(List<GfCommits> gfCommits,String startDate, String endDate) {
        HashMap<LocalDate, Integer> dateIntegerHashMap = new HashMap<>();
        putDaysInMap(0, dateIntegerHashMap, startDate, endDate);
        checkWhichDaysWereNotCommitted(gfCommits, 0, dateIntegerHashMap, startDate, endDate);
        return dateIntegerHashMap;
    }

    private void putDaysInMap(int value, HashMap<LocalDate, Integer> dateIntegerHashMap, String startDate, String endDate) {
        for (LocalDate weekday : getDaysBetween(startDate, endDate)) {
            dateIntegerHashMap.put(weekday,value);
        }
    }

    private void checkWhichDaysWereNotCommitted(List<GfCommits> gfCommits, int value, HashMap<LocalDate, Integer> dateIntegerHashMap, String startDate, String endDate) {
        for (int i = 0; i < gfCommits.size(); i++) {
            String date = gfCommits.get(i).getCommit().getAuthor().getDate();
            LocalDate inputlocalDate = convertToLocalDate(date.substring(0, 10));
            for (LocalDate weekday : getDaysBetween(startDate, endDate)) {
                if (inputlocalDate.equals(weekday)) {
                    value += 1;
                    dateIntegerHashMap.put(weekday, value);
                }
            }
        }
    }

    private List<LocalDate> getDaysBetween(String startDate, String endDate) {
        LocalDate startD = convertToLocalDate(startDate);
        LocalDate endD = convertToLocalDate(endDate);
        List<LocalDate> weekDaysBetween = new ArrayList<>();
        while (!startD.isAfter(endD)) {
            if(startD.getDayOfWeek() != DayOfWeek.SATURDAY && startD.getDayOfWeek() != DayOfWeek.SUNDAY)
            weekDaysBetween.add(startD);
            startD = startD.plusDays(1);
        }
        return weekDaysBetween;
    }

    public LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}

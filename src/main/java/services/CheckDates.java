package services;

import models.GfCommits;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class CheckDates {

    public String getPreviousWeekStartDate() {
        LocalDate sameDayLastWeek = getLastWeekDate();
        return String.valueOf(sameDayLastWeek.with(DayOfWeek.MONDAY));
    }

    public String getPreviousWeekEndDate() {
        LocalDate sameDayLastWeek = getLastWeekDate();
        return String.valueOf(sameDayLastWeek.with(DayOfWeek.FRIDAY));
    }

    private LocalDate getLastWeekDate() {
        LocalDate today = LocalDate.now();
        return today.minusWeeks(1);
    }

    public HashMap<LocalDate, Integer> daysOfNotCommiting(List<GfCommits> gfCommits) {
        HashMap<LocalDate, Integer> dateIntegerHashMap = new HashMap<>();
        putDaysInMap(0, dateIntegerHashMap);
        checkWhichDaysWereNotCommitted(gfCommits, 0, dateIntegerHashMap);
        return dateIntegerHashMap;
    }

    private void putDaysInMap(int value, HashMap<LocalDate, Integer> dateIntegerHashMap) {
        for (LocalDate weekday : getPreviousWeekDays()) {
            dateIntegerHashMap.put(weekday,value);
        }
    }

    public int checkHowManyDaysNotCommitted(List<GfCommits> gfCommits) {
        int count = 0;
        HashMap<LocalDate, Integer> myMap = daysOfNotCommiting(gfCommits);
        for (Map.Entry entry : myMap.entrySet()) {
            if (entry.getValue().equals(0)){
                count++;
            }
        }
        return count;
    }

    private void checkWhichDaysWereNotCommitted(List<GfCommits> gfCommits, int value, HashMap<LocalDate, Integer> dateIntegerHashMap) {
        for (int i = 0; i < gfCommits.size(); i++) {
            String date = gfCommits.get(i).getCommit().getAuthor().getDate();
            LocalDate inputlocalDate = convertToLocalDate(date);
            for (LocalDate weekday : getPreviousWeekDays()) {
                if (inputlocalDate.equals(weekday)) {
                    value += 1;
                    dateIntegerHashMap.put(weekday, value);
                }
            }
        }
    }

    private LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return LocalDate.parse(date, formatter);
    }

    private List<LocalDate> getPreviousWeekDays() {
        List<LocalDate> prevWeekDays = new ArrayList<>();
        LocalDate sameDayLastWeek = getLastWeekDate();
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.MONDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.TUESDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.WEDNESDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.THURSDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.FRIDAY));
        return prevWeekDays;
    }
}

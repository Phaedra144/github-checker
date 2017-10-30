package services;

import models.GfCommits;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class CheckDates {

    public boolean isPreviousWeek(String dateInString) {
        LocalDate inputlocalDate = convertToLocalDate(dateInString);
        LocalDate today = LocalDate.now();
        LocalDate sameDayLastWeek = today.minusWeeks(1);
        LocalDate mondayLastWeek = sameDayLastWeek.with(DayOfWeek.MONDAY);
        LocalDate saturdayLastWeek = sameDayLastWeek.with(DayOfWeek.SATURDAY);
        return getDatesBetweenUsingJava8(mondayLastWeek, saturdayLastWeek).contains(inputlocalDate);
    }

    public static List<LocalDate> getDatesBetweenUsingJava8(
            LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    public HashMap<LocalDate, Integer> daysOfNotCommiting(List<GfCommits> gfCommits) {
        int value = 0;
        HashMap<LocalDate, Integer> dateIntegerHashMap = new HashMap<>();
        putDaysInMap(value, dateIntegerHashMap);
        checkWhichDaysWereNotCommitted(gfCommits, value, dateIntegerHashMap);
        return dateIntegerHashMap;
    }

    private void putDaysInMap(int value, HashMap<LocalDate, Integer> dateIntegerHashMap) {
        for (LocalDate weekday : getPreviousWeekDays()) {
            dateIntegerHashMap.put(weekday,value);
        }
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
        LocalDate today = LocalDate.now();
        LocalDate sameDayLastWeek = today.minusWeeks(1);
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.MONDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.TUESDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.WEDNESDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.THURSDAY));
        prevWeekDays.add(sameDayLastWeek.with(DayOfWeek.FRIDAY));
        return prevWeekDays;
    }
}

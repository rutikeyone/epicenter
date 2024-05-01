package com.example.organizerforlaserhairremovalsalon.Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarUtils {
    public static LocalDate dateSelected;
    public static EventEntity eventEntitySelected;

    public static List<LocalDate> getDaysOfMonthArray(LocalDate localDate) {
        List<LocalDate> result = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(localDate);
        int lengthOfMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = CalendarUtils.dateSelected.withDayOfMonth(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i < 42; i++) {
            result.add(i > lengthOfMonth + dayOfWeek || i <= dayOfWeek
                    ? null
                    : LocalDate.of(dateSelected.getYear(), dateSelected.getMonth(), i - dayOfWeek));
        }

        return result;
    }

    public static List<LocalDate> getDaysOfWeekArray(LocalDate localDate) {
        List<LocalDate> result = new ArrayList<>();
        LocalDate currentLocalDate = sundayForDate(localDate);
        LocalDate endDate = currentLocalDate.plusWeeks(1);

        while (currentLocalDate.isBefore(endDate)) {
            result.add(currentLocalDate);
            currentLocalDate = currentLocalDate.plusDays(1);
        }

        return result;
    }

    private static LocalDate sundayForDate(LocalDate localDate) {
        LocalDate lastPositionToFind = localDate.minusWeeks(1);

        while (localDate.isAfter(lastPositionToFind)) {
            if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return localDate;
            }

            localDate = localDate.minusDays(1);
        }

        return null;
    }

    public static String getMonthAndYearOfDate(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return  localDate.format(dateTimeFormatter);
    }

    public static String timeFormat(LocalTime localTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("kk:mm");
        return  localTime.format(dateTimeFormatter);
    }

    public static String dateFormat(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return localDate.format(dateTimeFormatter);
    }
}

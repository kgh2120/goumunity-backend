package com.ssafy.goumunity.common.util;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class TimeUtils {

    public TimeUtils() {
        throw new IllegalStateException("do not create this class");
    }

    @AllArgsConstructor
    @Getter
    public static class TimeRange {
        private Instant start;
        private Instant end;
    }

    public enum TimeKey {
        DAY,
        WEEK,
        MONTH
    }

    public static TimeRange getTimeRange(TimeKey key) {
        return switch (key) {
            case DAY -> getToday();
            case WEEK -> getThisWeek();
            case MONTH -> getThisMonth();
        };
    }

    private static TimeRange getToday() {
        LocalDate now = LocalDate.now();
        Instant start = ZonedDateTime.of(now.atStartOfDay(), ZoneId.of("UTC")).toInstant();
        Instant end = ZonedDateTime.of(LocalTime.MAX.atDate(now), ZoneId.of("UTC")).toInstant();
        return new TimeRange(start, end);
    }

    private static TimeRange getThisWeek() {
        LocalDateTime now = LocalDateTime.now();
        Instant startOfWeek =
                ZonedDateTime.of(now.with(DayOfWeek.MONDAY), ZoneId.of("UTC")).toInstant();
        Instant endOfWeek = ZonedDateTime.of(now.with(DayOfWeek.SUNDAY), ZoneId.of("UTC")).toInstant();
        return new TimeRange(startOfWeek, endOfWeek);
    }

    private static TimeRange getThisMonth() {
        LocalDateTime now = LocalDateTime.now();
        Instant startOfWeek = ZonedDateTime.of(now.withDayOfMonth(1), ZoneId.of("UTC")).toInstant();
        Instant endOfWeek =
                ZonedDateTime.of(now.with(TemporalAdjusters.lastDayOfMonth()), ZoneId.of("UTC"))
                        .toInstant();
        return new TimeRange(startOfWeek, endOfWeek);
    }
}

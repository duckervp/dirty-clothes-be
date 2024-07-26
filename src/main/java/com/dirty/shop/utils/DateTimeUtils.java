package com.dirty.shop.utils;


import jakarta.annotation.Nullable;
import org.apache.logging.log4j.util.Strings;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {

    public static Instant getCurrentInstantMilliseconds() {
        return Instant.now();
    }

    public static Instant plusDays(Instant input, long amount) {
        return input.plus(amount, ChronoUnit.DAYS);
    }

    public static Instant plusMinutes(Instant input, long amount) {
        if (Objects.isNull(input)) {
            return null;
        }
        return input.plus(amount, ChronoUnit.MINUTES);
    }

    public static Instant plusMils(Instant input, long amount) {
        return input.plus(amount, ChronoUnit.MILLIS);
    }

    public static Instant plusHours(Instant input, long amount) {
        return input.plus(amount, ChronoUnit.HOURS);
    }

    public static Instant minusHours(Instant input, long amount) {
        return input.minus(amount, ChronoUnit.HOURS);
    }

    public static Instant minusDays(Instant input, long amount) {
        return input.minus(amount, ChronoUnit.DAYS);
    }

    public static Instant minusMinutes(Instant input, long amount) {
        return input.minus(amount, ChronoUnit.MINUTES);
    }

    public static Instant now() {
        return Instant.now();
    }

    public static Instant last1Hour(@Nullable Instant instant) {
        instant = Objects.isNull(instant) ? now() : instant;
        return minusHours(instant, 1);
    }

    public static Instant last1Day(@Nullable Instant instant) {
        instant = Objects.isNull(instant) ? now() : instant;
        return minusDays(instant, 1);
    }

    public static Instant toInstant(Long timestamp) {
        return Objects.isNull(timestamp) ? null : Instant.ofEpochMilli(timestamp);
    }

    public static Instant toInstantSecond(Long timestamp) {
        if (Objects.isNull(timestamp) ) {
            return null;
        }
        long second = timestamp / 1000;
        return Instant.ofEpochSecond(second);
    }

    // from latest dev 0502
    public static Instant currentDateUTC() {
        return Instant.now();
    }

    public static boolean isBefore(Instant time1, Instant time2) {
        return time1.isBefore(time2);
    }

    public static boolean isBeforeNow(Instant time) {
        return time.isBefore(Instant.now());
    }

    public static boolean isAfter(Instant time1, Instant time2) {
        return time1.isAfter(time2);
    }

    public static boolean isAfterNow(Instant time) {
        return time.isAfter(Instant.now());
    }

    public static Timestamp toTimestamp(Instant instant) {
        if (Objects.isNull(instant)) {
            return null;
        }
        return Timestamp.from(instant);
    }

    public static Instant toInstant(Timestamp timestamp) {
        return Objects.isNull(timestamp) ? null : timestamp.toInstant();
    }

    public static String toTimestampNano(Instant instant) {
        return Objects.isNull(instant) ? null : String.valueOf(toTimestamp(instant).getTime());
    }

    public static Date toDate(Instant instant) {
        return Timestamp.from(instant);
    }

//    public static List<Timestamp> getTimeChunks(int total, int chunkSize, TemporalUnit unit) {
//        if (chunkSize == 0) {
//            throw new ApiException(SystemApiCode.GET_TIME_CHUNKS_CHUNK_SIZE_MUST_NOT_BE_ZERO);
//        }
//        Instant now = Instant.now();
//        int totalChunk = (int) Math.ceil((double) total / chunkSize);
//        return IntStream.range(0, totalChunk + 1).boxed().sorted(Comparator.reverseOrder())
//            .map(i -> Timestamp.from(now.minus((long) i * chunkSize, unit)))
//            .collect(Collectors.toList());
//    }

    public static Instant startOfDay(Instant instant) {
        return instant.truncatedTo(ChronoUnit.DAYS);
    }

    public static Instant endOfDay(Instant instant) {
        return instant.truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)
            .minus(1, ChronoUnit.SECONDS);
    }

//    public static Instant toInstant(String date, String format) {
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        try {
//            return sdf.parse(date).toInstant();
//        } catch (Exception e) {
//            throw new ApiException(SystemApiCode.INVALID_DATE_FORMAT);
//        }
//    }

//    public static Instant toInstant(String date, List<String> formats) {
//        for (String format : formats) {
//            SimpleDateFormat sdf = new SimpleDateFormat(format);
//            try {
//                return sdf.parse(date).toInstant();
//            } catch (Exception e) {}
//        }
//        throw new ApiException(SystemApiCode.INVALID_DATE_FORMAT);
//    }

    public static long daysBetween(Instant before, Instant after) {
        return Duration.between(before, after).toDays();
    }

    public static long milsBetween(Instant before, Instant after) {
        return Duration.between(before, after).toMillis();
    }

    public static long minBetween(Instant before, Instant after) {
        return Duration.between(before, after).toMinutes();
    }

    public static String format(Instant instant, String format) {
        if (Objects.isNull(instant)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(Date.from(instant));
    }

    public static Instant startOfMonth(YearMonth yearMonth) {
        return startOfDay(yearMonth.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static String toString(Timestamp timestamp) {
        return Objects.isNull(timestamp) ? Strings.EMPTY : timestamp.toString();
    }

    public static Instant endOfMonth(YearMonth yearMonth) {
        return yearMonth.atEndOfMonth().atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
    }

    public static Instant startOfWeek(Instant instant) {
        DayOfWeek dow = DayOfWeek.of(1);

        ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));

        ZonedDateTime adjusted = zdt.with(TemporalAdjusters.previousOrSame(dow));

        return startOfDay(adjusted.toInstant());
    }

    public static Instant endOfWeek(Instant instant) {
        DayOfWeek dow = DayOfWeek.of(7);

        ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));

        ZonedDateTime adjusted = zdt.with(TemporalAdjusters.nextOrSame(dow));

        return endOfDay(adjusted.toInstant());
    }

    public static String formatOffsetTime(OffsetTime offsetTime) {
        if (Objects.isNull(offsetTime)) {
            return null;
        }
        return offsetTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static OffsetTime toOffsetTime(LocalTime time) {
        return OffsetTime.of(time, ZoneOffset.UTC);
    }

    public static boolean isCurrentMonth(Instant date) {
        if (Objects.isNull(date)) {
            return false;
        }

        ZonedDateTime dateAtUTC = date.atZone(ZoneOffset.UTC);
        ZonedDateTime dateNow = Instant.now().atZone(ZoneOffset.UTC);
        return dateAtUTC.getMonth() == dateNow.getMonth() && dateAtUTC.getYear() == dateNow.getYear();
    }

    public static boolean isEqualDate(Instant a, Instant b) {
        return a.truncatedTo(ChronoUnit.DAYS).equals(b.truncatedTo(ChronoUnit.DAYS));
    }

    public static boolean isLastMonth(Instant date) {
        if (Objects.isNull(date)) {
            return false;
        }

        ZonedDateTime dateAtUTC = date.atZone(ZoneOffset.UTC);
        ZonedDateTime lastMonth = Instant.now().atZone(ZoneOffset.UTC).minusMonths(1L);
        return dateAtUTC.getMonth() == lastMonth.getMonth() && dateAtUTC.getYear() == lastMonth.getYear();
    }

    private Instant startOfMonth(Instant date) {
        LocalDate localDate = date.atZone(ZoneOffset.UTC).toLocalDate().withDayOfMonth(1);
        return localDate.atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    }

    public static Instant getHourTruncatedCurrentDate() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime hourTruncatedDateTime = now.truncatedTo(ChronoUnit.HOURS);
        return hourTruncatedDateTime.toInstant();
    }

//    public static Instant toTZLocalTime(Instant instant) {
//        Long offsetMinutes = TimeZoneContextHolder.getCurrentTimeZoneOffset();
//        return Objects.nonNull(instant) ? instant.minusSeconds(offsetMinutes * 60) : null;
//    }
//
//    public static Instant toUTCFromTZLocalTime(Instant instant) {
//        Long offsetMinutes = TimeZoneContextHolder.getCurrentTimeZoneOffset();
//        return Objects.nonNull(instant) ? instant.plusSeconds(offsetMinutes * 60) : null;
//    }

//    public static Instant getUserStartOfMonthFilter() {
//        LocalDate userCurrentDate = Objects.requireNonNull(DateTimeUtils.toTZLocalTime(Instant.now()))
//                .atZone(ZoneOffset.UTC).toLocalDate();
//
//        Instant startOfMonth = userCurrentDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
//        startOfMonth = DateTimeUtils.toUTCFromTZLocalTime(startOfMonth);
//
//        return startOfMonth;
//    }
//
//    public static Instant getUserEndOfMonthFilter() {
//        LocalDate userCurrentDate = Objects.requireNonNull(DateTimeUtils.toTZLocalTime(Instant.now()))
//                .atZone(ZoneOffset.UTC).toLocalDate();
//
//        Instant endOfMonth = userCurrentDate.with(TemporalAdjusters.lastDayOfMonth())
//                .atTime(23, 59, 59, 0).atZone(ZoneOffset.UTC).toInstant();
//        endOfMonth = DateTimeUtils.toUTCFromTZLocalTime(endOfMonth);
//
//        return endOfMonth;
//    }

}

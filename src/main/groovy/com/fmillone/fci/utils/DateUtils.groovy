package com.fmillone.fci.utils

import groovy.transform.CompileStatic

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId

import static java.time.DayOfWeek.*
import static java.time.temporal.TemporalAdjusters.next
import static java.time.temporal.TemporalAdjusters.previous

@CompileStatic
class DateUtils {

    private static final int oneMonth = 1
    private static final ZoneId BUENOS_AIRES = ZoneId.of("America/Buenos_Aires")
    private static final int oneDay = 1
    private static final List<DayOfWeek> WEEKEND = [SATURDAY,SUNDAY].asImmutable()
    private static final List<DayOfWeek> fridayOrSaturday = [FRIDAY, SATURDAY].asImmutable()
    private static final List<DayOfWeek> mondayOrSunday = [MONDAY, SUNDAY].asImmutable()


    static LocalDate aMonthBefore(LocalDate date = today) {
        date.minusMonths(oneMonth)
    }

    static LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(BUENOS_AIRES)
                .toLocalDate()
    }

    static LocalDate nextWeekday(LocalDate date) {
        isFridayOrSaturday(date) ? date.with(next(MONDAY)) : date.plusDays(oneDay)
    }

    private static boolean isFridayOrSaturday(LocalDate date) {
        date.dayOfWeek in fridayOrSaturday
    }

    static LocalDate previousWeekday(LocalDate date) {
        isMondayOrSunday(date) ? date.with(previous(FRIDAY)) : date.minusDays(oneDay)
    }

    private static boolean isMondayOrSunday(LocalDate date) {
        date.dayOfWeek in mondayOrSunday
    }

    static boolean isWeekend(LocalDate date) {
        date.dayOfWeek in WEEKEND
    }

    static LocalDate getAmonthAgo() {
        aMonthBefore()
    }

    static LocalDate getToday() {
        LocalDate.now()
    }

    static LocalDate aDayBefore(LocalDate date){
        date.minusDays oneDay
    }

    static LocalDate aDayAfter(LocalDate date){
        date.plusDays oneDay
    }

    static Date toDate(LocalDate localDate) {
        new Date(localDate
                .atStartOfDay(BUENOS_AIRES)
                .toEpochSecond() * 1000)
    }
}

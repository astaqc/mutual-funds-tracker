package com.fmillone.fci.utils

import groovy.transform.CompileStatic

import static java.util.Calendar.*

@CompileStatic
class DateUtils {

    static Date aMonthBefore(Date date = new Date()) {
        Calendar calendar = date.toCalendar()
        calendar.add(MONTH, -1)
        return calendar.time
    }

    static Date nextWeekday(Date date) {
        Calendar cal = date.toCalendar()
        cal.add(DATE, 1)
        while (!isWeekDay(cal)) {
            cal.add(DATE, 1)
        }
        return cal.time
    }

    static boolean isWeekDay(Calendar cal) {
        cal.get(DAY_OF_WEEK) in (MONDAY..FRIDAY)
    }

    static Date getAmonthAgo() {
        aMonthBefore()
    }
}

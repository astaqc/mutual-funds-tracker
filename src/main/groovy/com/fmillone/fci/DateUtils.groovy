package com.fmillone.fci

class DateUtils {

    static Date aMonthBefore(Date date = new Date()) {
        Calendar calendar = date.toCalendar()
        calendar.add(Calendar.MONTH, -1)
        return calendar.getTime()
    }

    static Date getAmonthAgo(){
        aMonthBefore()
    }
}

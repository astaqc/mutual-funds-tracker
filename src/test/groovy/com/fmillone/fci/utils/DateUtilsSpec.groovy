package com.fmillone.fci.utils

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.*

class DateUtilsSpec extends Specification {


    void "it should convert a LocalDate to Date"() {
        expect:
            toLocalDate(toDate(today)) == today
    }

    @Unroll
    void "it should evaluate if is a weekend day"() {
        expect:
            isWeekend(LocalDate.parse(date)) == expected
        where:
            date         | expected
            '2017-12-17' | true
            '2017-12-18' | false

    }
}

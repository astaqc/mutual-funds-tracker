package com.fmillone.fci.fundStatus

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.time.format.DateTimeFormatter

class TrustStatusUtils {

    static {
        spanishFormatSymbols = DecimalFormatSymbols.instance
        spanishFormatSymbols.with {
            decimalSeparator = ',' as char
            groupingSeparator = '.' as char
        }
    }

    static DateTimeFormatter getDateFormatter() {
        DateTimeFormatter.ofPattern('yyyy-MM-dd')
    }

    static DateTimeFormatter getTableDateFormat() {
        DateTimeFormatter.ofPattern('dd/MM/yyyy')
    }

    private static DecimalFormatSymbols spanishFormatSymbols

    static NumberFormat getValuesPerUnitFormat() {
        new DecimalFormat('#,####', spanishFormatSymbols)
    }
}

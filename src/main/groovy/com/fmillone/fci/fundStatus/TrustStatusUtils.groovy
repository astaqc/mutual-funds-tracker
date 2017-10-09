package com.fmillone.fci.fundStatus

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat

class TrustStatusUtils {

    static {
        spanishFormatSymbols = DecimalFormatSymbols.instance
        spanishFormatSymbols.with {
            decimalSeparator = ',' as char
            groupingSeparator = '.' as char
        }
    }

    static DateFormat getDateFormatter() {
        return new SimpleDateFormat('yyyy-MM-dd', Locale.ENGLISH)
    }

    static DateFormat getTableDateFormat() {
        return new SimpleDateFormat('dd/MM/yyyy', Locale.ENGLISH)
    }

    private static DecimalFormatSymbols spanishFormatSymbols

    static NumberFormat getValuesPerUnitFormat() {
        new DecimalFormat('#,####', spanishFormatSymbols)
    }
}

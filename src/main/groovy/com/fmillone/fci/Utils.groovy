package com.fmillone.fci

import java.text.DateFormat
import java.text.SimpleDateFormat

class TrustStatusUtils {
    static DateFormat getDateFormatter() {
        return new SimpleDateFormat('yyyy-MM-dd', Locale.ENGLISH)
    }

    static DateFormat getTableDateFormat(){
        return new SimpleDateFormat('dd/MM/yyyy', Locale.ENGLISH)
    }
}

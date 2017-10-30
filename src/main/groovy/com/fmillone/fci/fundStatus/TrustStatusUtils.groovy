package com.fmillone.fci.fundStatus

import java.time.format.DateTimeFormatter

class TrustStatusUtils {

    static DateTimeFormatter getDateFormatter() {
        DateTimeFormatter.ofPattern('yyyy-MM-dd')
    }

}

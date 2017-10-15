package com.fmillone.fci.fundStatus.remote

import com.fmillone.fci.fundStatus.TrustStatus
import groovy.util.slurpersupport.Node

import java.time.LocalDate

import static com.fmillone.fci.fundStatus.TrustStatusUtils.getValuesPerUnitFormat
import static java.lang.Long.valueOf

class FundStatusExtractor {

    private static final Map<String, Closure> extractors = [
            Nombre  : { TrustStatus t, String value -> t.name = value },
            Horiz   : { TrustStatus t, String value -> t.horiz = value },
            VCP     : { TrustStatus t, String value -> t.valuesPerUnity = valuesPerUnitFormat.parse(value).doubleValue() },
            QCP     : { TrustStatus t, String value -> t.amountOfPieces = valueOf(value.replaceAll(/\./, '')) },
            PN      : { TrustStatus t, String value -> t.totalValue = valueOf(value.replaceAll(/\./, '')) }
    ].withDefault {{t,v-> }}.asImmutable()

    static TrustStatus extract(List<Node> row, LocalDate date) {
        TrustStatus trustStatus = new TrustStatus(date: date)
        row.each {
            extractProperty(it.name(),trustStatus, it.text())
        }
        return trustStatus
    }

    private static void extractProperty(String key, TrustStatus status, String value){
        extractors[key](status,value)
    }
}

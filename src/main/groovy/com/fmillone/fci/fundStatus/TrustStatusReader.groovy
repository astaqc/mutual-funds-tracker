package com.fmillone.fci.fundStatus

import com.fmillone.fci.fundStatus.remote.RemoteTrustStatusClient
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.Node
import groovy.util.slurpersupport.NodeChildren
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

import static com.fmillone.fci.fundStatus.TrustStatusUtils.tableDateFormat
import static com.fmillone.fci.fundStatus.TrustStatusUtils.valuesPerUnitFormat
import static com.fmillone.fci.utils.DateUtils.nextWeekday
import static java.lang.Long.valueOf

class TrustStatusReader implements ItemReader<TrustStatus> {

    private static Map<String, Closure> extractors = [
            Nombre  : { TrustStatus t, String value -> t.name = value },
            Fecha   : { TrustStatus t, String value -> t.date = tableDateFormat.parse(value) },
            Horiz   : { TrustStatus t, String value -> t.horiz = value },
            VCP     : { TrustStatus t, String value -> t.valuesPerUnity = valuesPerUnitFormat.parse(value).doubleValue() },
            QCP     : { TrustStatus t, String value -> t.amountOfPieces = valueOf(value.replaceAll(/\./, '')) },
            PN      : { TrustStatus t, String value -> t.totalValue = valueOf(value.replaceAll(/\./, '')) },
            Espacios: { t, v -> }
    ].asImmutable()

    private static final String HORIZ_DEFAULT_VALUE = 'N'
    private static final int HORIZ_KEY = 2

    RemoteTrustStatusClient remoteTrustStatusClient
    Date currentDate
    Date to = new Date()
    Iterator<Node> rawTrustStatuses

    @Override
    TrustStatus read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (currentDate >= to) {
            return null
        }

        if (shouldGetNextBatch()) {
            fetchNextBatch()
        }

        return findNextTrustStatus()
    }

    private boolean shouldGetNextBatch() {
        rawTrustStatuses == null || !rawTrustStatuses.hasNext()
    }

    private TrustStatus findNextTrustStatus() {
        List<Node> row = rawTrustStatuses.next().children()
        while (!isValid(row)) {
            row = rawTrustStatuses.next().children()
        }
        return extractTrustStatus(row)
    }

    private static boolean isValid(List<Node> row) {
        row[HORIZ_KEY].text() != HORIZ_DEFAULT_VALUE
    }

    private TrustStatus extractTrustStatus(List<Node> raw) {
        TrustStatus trustStatus = new TrustStatus()
        raw.each {
            extractors[it.name()].call(trustStatus, it.text())
        }
        return trustStatus
    }

    private void fetchNextBatch() {
        GPathResult response = remoteTrustStatusClient.fetch(currentDate)
        currentDate = nextWeekday(currentDate)
        setRawTrustStatuses(response)
    }

    void setRawTrustStatuses(GPathResult response) {
        this.rawTrustStatuses = ((NodeChildren) response.Datos).childNodes()
    }
}

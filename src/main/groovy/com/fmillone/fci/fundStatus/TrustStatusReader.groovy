package com.fmillone.fci.fundStatus

import com.fmillone.fci.fundStatus.remote.RemoteTrustStatusClient
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.Node
import groovy.util.slurpersupport.NodeChildren
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException

import java.time.LocalDate

import static com.fmillone.fci.fundStatus.remote.FundStatusExtractor.extract
import static com.fmillone.fci.utils.DateUtils.isWeekend
import static com.fmillone.fci.utils.DateUtils.nextWeekday
import static com.fmillone.fci.utils.DateUtils.previousWeekday

@CompileStatic
class TrustStatusReader implements ItemReader<TrustStatus> {


    private static final String HORIZ_DEFAULT_VALUE = 'N'
    private static final int HORIZ_KEY = 2

    RemoteTrustStatusClient remoteTrustStatusClient
    LocalDate currentDate
    LocalDate to = LocalDate.now()
    Iterator<Node> rawTrustStatuses

    @Override
    TrustStatus read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (currentDate > to) {
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
        return extract(row, currentDate)
    }

    private static boolean isValid(List<Node> row) {
        row[HORIZ_KEY].text() != HORIZ_DEFAULT_VALUE
    }

    private void fetchNextBatch() {
        updateCurrentDate()
        GPathResult response = remoteTrustStatusClient.fetch(currentDate)
        setRawTrustStatuses(response)
    }

    private void updateCurrentDate() {
        currentDate = nextWeekday(currentDate)
    }

    void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate.minusDays(1L)
    }

    void setTo(LocalDate date) {
        to = isWeekend(date) ? previousWeekday(date) : date
    }

    @CompileDynamic
    void setRawTrustStatuses(GPathResult response) {
        this.rawTrustStatuses = ((NodeChildren) response.Datos).childNodes()
    }
}

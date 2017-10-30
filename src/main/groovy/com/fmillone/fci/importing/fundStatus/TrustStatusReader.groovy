package com.fmillone.fci.importing.fundStatus

import com.fmillone.fci.fundStatus.TrustStatus
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.*

@CompileStatic
@Log4j
class TrustStatusReader implements ItemReader<TrustStatus> {

    RemoteFundStatusService service
    LocalDate currentDate
    LocalDate to = LocalDate.now()
    Iterator<RemoteFundStatus> rawTrustStatuses

    @Override
    TrustStatus read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (shouldGetNextBatch()) {
            fetchNextBatch()
        }
        return areDatesValid() ? findNextTrustStatus() : null
    }

    private boolean areDatesInvalid() {
        currentDate > to
    }

    private boolean shouldGetNextBatch() {
        areDatesValid() && (rawTrustStatuses == null || !rawTrustStatuses.hasNext())
    }

    private boolean areDatesValid() {
        !areDatesInvalid()
    }

    private TrustStatus findNextTrustStatus() {
        RemoteFundStatus fundStatus = rawTrustStatuses.next()
        while (!fundStatus.isValid()) {
            fundStatus = rawTrustStatuses.next()
        }
        return fundStatus.toTrustStatus(currentDate)
    }

    private void fetchNextBatch() {
        updateCurrentDate()
        List response = service.fetchByTypeAndDate(currentDate)
        rawTrustStatuses = response.iterator()
        if(shouldGetNextBatch()){
            log.warn "no data for day $currentDate"
            fetchNextBatch()
        }
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

}

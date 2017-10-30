package com.fmillone.fci.fundStatus

import spock.lang.Specification

import java.time.LocalDate

import static com.fmillone.fci.fundStatus.FundStatusFixture.someFundStatus
import static com.fmillone.fci.fundStatus.FundStatusFixture.someOtherFundStatus
import static com.fmillone.fci.utils.DateUtils.*

class FundServiceSpec extends Specification {

    public static final Double STARTING_AMOUNT = 100.0D
    public static final String SOME_FUND = 'some fund'
    FundService service
    TrustStatusRepository repository

    void setup() {
        repository = Mock(TrustStatusRepository)
        service = new FundService(repository: repository)
    }

    void cleanup() {
    }

    void "it should get all by date greater than a date"() {
        given:
            LocalDate date = LocalDate.now()
            List expected = [someFundStatus]
        when:
            List<TrustStatus> actual = service.getAllByDateGreaterThan(date)
        then:
            1 * repository.findAllByDateGreaterThan(date) >> expected
        and:
            actual.is expected
    }

    void "it should calculate gain"() {
        given:
            LocalDate since = LocalDate.of(2017, 12, 12)
            LocalDate to = LocalDate.of(2017, 12, 18)
        when:
            Gain gain = service.calculateGain(SOME_FUND, STARTING_AMOUNT, since, to)
        then:
            1 * repository.findByNameAndDate(SOME_FUND, since) >> Optional.of(someFundStatus)
            1 * repository.findByNameAndDate(SOME_FUND, to) >> Optional.of(someOtherFundStatus)
        and:
            round(gain.percentGain) == 14.28
            round(gain.realGain) == 14.28
            round(gain.total) == 114.28
    }

    void "it should calculate gain (today)"() {
        given:
            LocalDate since = LocalDate.of(2017, 12, 12)
            LocalDate to = isWeekend() ? previousWeekday() : today
        when:
            Gain gain = service.calculateGain(SOME_FUND, STARTING_AMOUNT, since)
        then:
            1 * repository.findByNameAndDate(SOME_FUND, since) >> Optional.of(someFundStatus)
            1 * repository.findByNameAndDate(SOME_FUND, to) >> Optional.of(someOtherFundStatus)
        and:
            round(gain.percentGain) == 14.28
            round(gain.realGain) == 14.28
            round(gain.total) == 114.28
    }

    private static Double round(Double aDouble) {
        Math.floor(aDouble * 100) / 100
    }
}

package com.fmillone.fci.fundStatus

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.amonthAgo
import static java.time.LocalDate.parse as localDate

class TrustStatusControllerSpec extends BaseControllerSpec {

    TrustStatusController controller
    FundService service

    void setup() {
        service = Mock(FundService)
        controller = new TrustStatusController(fundService: service)
        setupMockMvc(controller)
    }

    void cleanup() {
    }

    void "it should respond a list of fund status with valid dates"() {
        given:
            LocalDate date = localDate('2017-06-15')
            TrustStatus fundStatus = FundStatusFixture.from(date: date)
        when:
            def response = performGET('/api/trustStatus/lastMonth')
        then:
            1 * service.getAllByDateGreaterThan(amonthAgo) >> [fundStatus]
        and:
            List jsonResponse = getJsonResponse(response)
            jsonResponse.size() == 1
            jsonResponse[0].date == 1497495600000L
    }

    void "it should respond a list of fund status since a date"() {
        given:
            LocalDate queryDate = localDate('2017-10-10')
            LocalDate date = localDate('2017-06-15')
            TrustStatus fundStatus = FundStatusFixture.from(date: date)
        when:
            def response = performGET('/api/trustStatus/since?date=Tue Oct 10 2017 21:00:00 GMT-0300 (-03)')
        then:
            1 * service.getAllByDateGreaterThan(queryDate) >> [fundStatus]
        and:
            List jsonResponse = getJsonResponse(response)
            jsonResponse.size() == 1
            jsonResponse[0].date == 1497495600000L
    }
}

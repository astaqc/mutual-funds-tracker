package com.fmillone.fci.fundStatus.remote

import com.fmillone.fci.FciApplication
import com.fmillone.fci.fundStatus.TrustStatusUtils
import com.fmillone.fci.importing.fundStatus.RemoteFundStatus
import com.fmillone.fci.importing.fundStatus.RemoteFundStatusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(classes = FciApplication)
class RemoteFundStatusRestClientISpec extends Specification {

    @Autowired
    RemoteFundStatusService service

    void setup() {
    }

    void cleanup() {
    }

    @Ignore('just for live debugging')
    void "it should fetch the current date trusts statuses"() {
        given:
            LocalDate date = LocalDate.parse('2017-09-27', TrustStatusUtils.dateFormatter)
        when:
            List<RemoteFundStatus> response = service.fetchByTypeAndDate(date)
        then:
            response != null
            !response.isEmpty()
            response.first() instanceof RemoteFundStatus
    }

}

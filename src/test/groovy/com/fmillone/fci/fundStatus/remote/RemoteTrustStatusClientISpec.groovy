package com.fmillone.fci.fundStatus.remote

import com.fmillone.fci.FciApplication
import com.fmillone.fci.fundStatus.TrustStatusUtils
import com.fmillone.fci.importing.fundStatus.RemoteTrustStatusClient
import groovy.util.slurpersupport.GPathResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(classes = FciApplication)
class RemoteTrustStatusClientISpec extends Specification {

    @Autowired
    RemoteTrustStatusClient remoteTrustStatusClient

    void setup() {
    }

    void cleanup() {
    }

    void "it should fetch the current date trusts statuses"() {
        given:
            LocalDate date = LocalDate.parse('2017-09-27', TrustStatusUtils.dateFormatter)
        when:
            GPathResult response = remoteTrustStatusClient.fetch(date)
        then:
            response != null
    }

}

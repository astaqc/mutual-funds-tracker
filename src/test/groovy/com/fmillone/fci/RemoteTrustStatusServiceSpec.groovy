package com.fmillone.fci

import groovy.util.slurpersupport.GPathResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = FciApplication)
class RemoteTrustStatusServiceSpec extends Specification {

    @Autowired
    RemoteTrustStatusClient remoteTrustStatusClient

    void setup() {
    }

    void cleanup() {
    }

    void "it should fetch the current date trusts statuses"() {
        given:
            Date date = TrustStatusUtils.dateFormatter.parse('2017-09-27')
        when:
            GPathResult response = remoteTrustStatusClient.fetch(date)
        then:
            response != null
    }

}

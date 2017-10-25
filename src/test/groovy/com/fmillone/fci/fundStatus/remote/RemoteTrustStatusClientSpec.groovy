package com.fmillone.fci.fundStatus.remote

import com.fmillone.fci.importing.fundStatus.RemoteTrustStatusClient
import spock.lang.Specification

class RemoteTrustStatusClientSpec extends Specification {

    RemoteTrustStatusClient remoteTrustStatusClient

    void setup(){
        this.remoteTrustStatusClient = new RemoteTrustStatusClient()
    }


    void "it should encode the request"() {
        given:
            String expected = "query=%3CColeccion%3E%3CParametros%3E%3CTRentaI%3E1%3C%2FTRentaI%3E%3CTRentaN%3ERenta%20Variable%3C%2FTRentaN%3E%3CFecha%3E2017-09-25%3C%2FFecha%3E%3CSeparador%3EP%3C%2FSeparador%3E%3C%2FParametros%3E%3C%2FColeccion%3E"
        when:
            String actual = remoteTrustStatusClient.buildFormBody('2017-09-25')
        then:
            actual == expected
    }

}

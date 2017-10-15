package com.fmillone.fci.fundStatus

import com.fmillone.fci.fundStatus.remote.RemoteTrustStatusClient
import groovy.util.slurpersupport.GPathResult
import spock.lang.Specification

import java.time.LocalDate

import static com.fmillone.fci.fundStatus.TrustStatusUtils.tableDateFormat
import static com.fmillone.fci.utils.DateUtils.aDayAfter
import static com.fmillone.fci.utils.DateUtils.aDayBefore
import static com.fmillone.fci.utils.DateUtils.today

class TrustStatusReaderSpec extends Specification {

    TrustStatusReader reader
    RemoteTrustStatusClient client

    void setup() {
        client = Mock(RemoteTrustStatusClient)
        reader = new TrustStatusReader(
                remoteTrustStatusClient: client
        )
    }


    void cleanup() {
    }

    GPathResult getResponse() {
        new XmlSlurper().parseText('''
<Coleccion>
    <Datos>
        <Dato>
            <Nombre>Consultatio Renta Variable - Clase B</Nombre>
            <Fecha>06/10/2017</Fecha>
            <Horiz>Lar   </Horiz>
            <VCP>16.074,503</VCP>
            <QCP>24.386.920</QCP>
            <PN>392.007.619</PN>
            <Espacios>3</Espacios>
        </Dato>
    </Datos>
</Coleccion>
''')
    }

    void "it should parse a date with spanish format"() {
        given:
            LocalDate someWednesday = LocalDate.of(2017, 10, 11)
            reader.with {
                to = aDayAfter someWednesday
                currentDate = someWednesday
            }
        when:
            TrustStatus trustStatus = reader.read()
        then:
            1 * client.fetch(someWednesday) >> response
        and:
            trustStatus.valuesPerUnity.toString() == '16074.503'
            trustStatus.name == 'Consultatio Renta Variable - Clase B'
            trustStatus.totalValue == 392007619L
            trustStatus.amountOfPieces == 24386920L
            trustStatus.date == someWednesday
    }

    void "it should fetch the data within the specified dates"() {
        given:
            LocalDate someWednesday = LocalDate.of(2017, 10, 11)
            reader.with {
                to = someWednesday
                currentDate = aDayBefore someWednesday
            }
        when:
            assert reader.read()
            assert reader.read()
        then:
            1 * client.fetch(aDayBefore(someWednesday)) >> response
            1 * client.fetch(someWednesday) >> response

    }
}

package com.fmillone.fci.fundStatus

import com.fmillone.fci.importing.fundStatus.RemoteFundStatus
import com.fmillone.fci.importing.fundStatus.RemoteFundStatusService
import com.fmillone.fci.importing.fundStatus.TrustStatusReader
import spock.lang.Specification

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.aDayAfter
import static com.fmillone.fci.utils.DateUtils.aDayBefore

class TrustStatusReaderSpec extends Specification {

    TrustStatusReader reader
    RemoteFundStatusService client

    void setup() {
        client = Mock(RemoteFundStatusService)
        reader = new TrustStatusReader(
                service: client
        )
    }


    void cleanup() {
    }

    List<RemoteFundStatus> getResponse() {
        [new RemoteFundStatus(
                fondo: 'Consultatio Renta Variable - Clase B',
                horizonte: 'Lar',
                cpp: 24386920L,
                patrimonio: 392007619.1,
                vcp: 16074.503
        )]
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
            1 * client.fetchByTypeAndDate(someWednesday) >> response
        and:
            trustStatus.valuesPerUnity.toString() == '16074.503'
            trustStatus.name == 'Consultatio Renta Variable - Clase B'
            trustStatus.totalValue == 392007619.1
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
            TrustStatus fistCall = reader.read()
            TrustStatus secondCall = reader.read()
        then:
            1 * client.fetchByTypeAndDate(aDayBefore(someWednesday)) >> response
            1 * client.fetchByTypeAndDate(someWednesday) >> response
        and:
            fistCall != null
            secondCall != null

    }
}

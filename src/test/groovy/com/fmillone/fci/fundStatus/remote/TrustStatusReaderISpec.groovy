package com.fmillone.fci.fundStatus.remote

import com.fmillone.fci.FciApplication
import com.fmillone.fci.fundStatus.RentType
import com.fmillone.fci.fundStatus.TrustStatus
import com.fmillone.fci.importing.fundStatus.RemoteFundStatusService
import com.fmillone.fci.importing.fundStatus.TrustStatusReader
import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.nextWeekday

@SpringBootTest(classes = FciApplication)
class TrustStatusReaderISpec extends Specification {

    ItemReader<TrustStatus> reader
    @Autowired
    RemoteFundStatusService remoteFundStatusService

    void setup() {
        LocalDate startDate = LocalDate.parse('2017-09-01')
        reader = new TrustStatusReader(
                service: remoteFundStatusService,
                currentDate: startDate,
                to: nextWeekday(startDate),
                rentType: RentType.VARIABLE
        )
    }

    @Ignore('only for live testing')
    void "it should read and extract one trust status"() {
        when:
            TrustStatus trustStatus = reader.read()
        then:
            !trustStatus.name.empty
    }
}

package com.fmillone.fci.fundStatus.remote

import com.fmillone.fci.FciApplication
import com.fmillone.fci.fundStatus.TrustStatus
import com.fmillone.fci.fundStatus.TrustStatusReader
import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.today

@SpringBootTest(classes = FciApplication)
class TrustStatusReaderISpec extends Specification {

    ItemReader<TrustStatus> reader
    @Autowired
    RemoteTrustStatusClient remoteTrustStatusClient

    void setup(){
        reader = new TrustStatusReader(
                remoteTrustStatusClient: remoteTrustStatusClient,
                currentDate: LocalDate.parse('2017-01-01'),
                to: today
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

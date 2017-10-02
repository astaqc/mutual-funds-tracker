package com.fmillone.fci

import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

import java.text.SimpleDateFormat

@SpringBootTest(classes = FciApplication)
class TrustStatusReaderSpec extends Specification {

    ItemReader<TrustStatus> reader
    @Autowired
    RemoteTrustStatusClient remoteTrustStatusClient

    void setup(){
        reader = new TrustStatusReader(
                remoteTrustStatusClient: remoteTrustStatusClient,
                currentDate: new SimpleDateFormat('yyyy-MM-dd').parse('2017-01-01'),
                to: new Date()
        )
    }

    @Ignore('only for live testing')
    void "it should read and extract one trust status"() {
        given:

        when:
            TrustStatus trustStatus = reader.read()
        then:
            !trustStatus.name.empty
    }
}

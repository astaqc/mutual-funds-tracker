package com.fmillone.fci.importing.fundStatus

import com.fmillone.fci.FciApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [FciApplication])
class CNVFundServiceSpec extends Specification {

    @Autowired
    CNVFundService service

    def "LastPage"() {
        when:
            def last = service.lastPageIds()
        then:
            last != null
    }
}

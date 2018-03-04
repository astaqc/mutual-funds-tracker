package com.fmillone.fci.importing.fundStatus

import com.fmillone.fci.FciApplication
import com.fmillone.fci.fundStatus.FundService
import com.fmillone.fci.importing.BatchImportService
import org.springframework.batch.core.JobExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.batch.core.BatchStatus.*

@SpringBootTest(classes = [FciApplication])
class CNVImportItCase extends Specification {

    @Autowired
    BatchImportService batchImportService

    @Autowired
    FundService fundService

    @Ignore('just for debugging')
    void "it should import all fund statuses for a day"() {
        given:
        when:
            JobExecution execution = batchImportService.startImportFromCNV()
        then:
            while (execution.status in [STARTED, STARTING, STOPPING]) {
//                wait
            }
            execution.status == COMPLETED
            fundService.repository.count() > 0
    }

}

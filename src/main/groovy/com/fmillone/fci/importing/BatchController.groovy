package com.fmillone.fci.importing

import com.fmillone.fci.fundStatus.RentType
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime

@CompileStatic
@RestController
class BatchController {

    @Autowired
    BatchImportService batchImportService

    @Autowired
    BatchStatusService batchStatusService

    @GetMapping(value = '/startJob')
    String startJob() {
        batchImportService.startImportFromCafci(RentType.VARIABLE)
        batchImportService.startImportFromCafci(RentType.FIX)
        return "started ${LocalDateTime.now()}"
    }

    @GetMapping(value = '/importStatus')
    String importStatus() {
        return batchStatusService.getJobExecution('importTrustStatuses')
    }
}

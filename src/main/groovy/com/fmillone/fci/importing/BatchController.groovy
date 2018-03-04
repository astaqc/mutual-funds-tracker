package com.fmillone.fci.importing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BatchController {

    @Autowired
    BatchImportService batchImportService

    @Autowired
    BatchStatusService batchStatusService

    @GetMapping( value = '/startJob')
    String startJob(){
        batchImportService.startImportFromCafci()
        return 'started ' + new Date().toString()
    }

    @GetMapping( value = '/importStatus')
    String importStatus(){
        return batchStatusService.getJobExecution('importTrustStatuses')
    }
}

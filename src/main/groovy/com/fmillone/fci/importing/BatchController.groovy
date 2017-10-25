package com.fmillone.fci.importing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BatchController {

    @Autowired
    BatchImportService batchImportService

    @GetMapping( value = '/startJob')
    String startJob(){
        batchImportService.start()
        return 'started ' + new Date().toString()
    }
}
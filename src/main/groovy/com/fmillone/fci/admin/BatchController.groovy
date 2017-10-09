package com.fmillone.fci.admin

import com.fmillone.fci.BatchImportService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class BatchController {

    @Autowired
    BatchImportService batchImportService

    @RequestMapping( value = '/startJob', method = RequestMethod.GET)
    String startJob(){
        batchImportService.start()
        return 'started ' + new Date().toString()
    }
}

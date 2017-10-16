package com.fmillone.fci.admin

import com.fmillone.fci.BatchImportService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
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

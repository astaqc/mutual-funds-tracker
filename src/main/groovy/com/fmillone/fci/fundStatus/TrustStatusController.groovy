package com.fmillone.fci.fundStatus

import com.fmillone.fci.utils.DateUtils
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.util.stream.Collectors

import static com.fmillone.fci.utils.DateUtils.amonthAgo
import static com.fmillone.fci.utils.DateUtils.toLocalDate
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping('/api/trustStatus')
@CompileStatic
class TrustStatusController {


    @Autowired
    TrustStatusRepository trustStatusRepository

    @RequestMapping(value = '/lastMonth', method = GET)
    List<FundStatusDTO> getLastMonth() {
        return trustStatusRepository
                .findAllByDateGreaterThan(amonthAgo)
                .collect(FundStatusDTO.&from)
    }

    @RequestMapping(value = '/since', method = GET)
    List<FundStatusDTO> getSince(@RequestParam Date date) {
        return trustStatusRepository
                .findAllByDateGreaterThan(toLocalDate(date))
                .collect(FundStatusDTO.&from)
    }


}

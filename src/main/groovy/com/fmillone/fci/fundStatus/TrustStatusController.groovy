package com.fmillone.fci.fundStatus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static com.fmillone.fci.utils.DateUtils.amonthAgo
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping('/api/trustStatus')
class TrustStatusController {


    @Autowired
    TrustStatusRepository trustStatusRepository

    @RequestMapping(value = '/lastMonth', method = GET)
    List<TrustStatus> getLastMonth() {
        return trustStatusRepository
                .findAllByDateGreaterThan(amonthAgo)
                .orElse([])
    }

    @RequestMapping(value = '/since', method = GET)
    List<TrustStatus> getSince(@RequestParam Date date) {
        return trustStatusRepository
                .findAllByDateGreaterThan(date)
                .orElse([])
    }


}

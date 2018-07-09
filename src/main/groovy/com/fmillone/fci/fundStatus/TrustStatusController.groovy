package com.fmillone.fci.fundStatus

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static com.fmillone.fci.utils.DateUtils.amonthAgo
import static com.fmillone.fci.utils.DateUtils.toLocalDate

@RestController
@RequestMapping('/api/trustStatus')
@CompileStatic
class TrustStatusController {

    @Autowired
    FundService fundService

    @GetMapping(value = '/lastMonth')
    List<FundStatusDTO> getLastMonth() {
        fundService
                .getAllByDateGreaterThan(amonthAgo)
                .collect(FundStatusDTO::from)
    }

    @GetMapping(value = '/since')
    List<FundStatusDTO> getSince(@RequestParam Date date) {
        fundService
                .getAllByDateGreaterThan(toLocalDate(date))
                .collect(FundStatusDTO::from)
    }

    @GetMapping(value = '/gain')
    Gain getGain(@RequestParam String fundName, @RequestParam Double initialInvestment, @RequestParam Date since) {
        fundService
                .calculateGain(fundName, initialInvestment, toLocalDate(since))
    }
}

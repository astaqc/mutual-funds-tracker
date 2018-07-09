package com.fmillone.fci.config

import com.fmillone.fci.importing.fundStatus.RemoteFundStatusService
import com.fmillone.fci.importing.fundStatus.TrustStatusReader
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CafciJobConfiguration {

    @Autowired
    RemoteFundStatusService service

    @Bean
    @StepScope
    TrustStatusReader trustStatusReader(@Value('#{jobParameters}') Map parameters) {
        new TrustStatusReader(
                service: service,
                currentDate: parameters.currentDate,
                to: parameters.to,
                rentType: parameters.rentType
        )
    }
}

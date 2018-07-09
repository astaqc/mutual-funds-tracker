package com.fmillone.fci.importing

import com.fmillone.fci.config.JobCompletionNotificationListener
import com.fmillone.fci.fundStatus.RentType
import com.fmillone.fci.fundStatus.TrustStatus
import com.fmillone.fci.importing.fundStatus.RemoteFundStatusService
import com.fmillone.fci.importing.fundStatus.TrustStatusReader
import groovy.transform.CompileStatic
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDate

import static com.fmillone.fci.utils.BatchUtils.parameters
import static com.fmillone.fci.utils.DateUtils.today

@Service
@CompileStatic
class BatchImportService {

    @Autowired
    JobBuilderFactory jobBuilderFactory
    @Autowired
    StepBuilderFactory stepBuilderFactory
    @Autowired
    RepositoryItemWriter<TrustStatus> repositoryItemWriter
    @Autowired
    JobCompletionNotificationListener jobCompletionNotificationListener
    @Autowired
    RemoteFundStatusService remoteFundStatusService
    @Autowired
    JobLauncher jobLauncher
    @Autowired @StepScope
    TrustStatusReader trustStatusReader

    JobExecution startImportFromCafci(RentType rentType) {
        jobLauncher.run(
                importTrustStatusJob(),
                parameters([
                        'rentType'   : rentType,
                        'currentDate': LocalDate.parse('2017-09-02'),
                        'to'         : today
                ])
        )
    }

    Job importTrustStatusJob() {
        return jobBuilderFactory.get('importTrustStatuses')
                .listener(jobCompletionNotificationListener)
                .flow(importFundStatusesStep)
                .end()
                .build()
    }

    Step getImportFundStatusesStep() {
        return stepBuilderFactory.get('import fund statuses')
                .<TrustStatus, TrustStatus> chunk(10)
                .reader(trustStatusReader)
                .writer(repositoryItemWriter)
                .build()
    }
}

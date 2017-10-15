package com.fmillone.fci

import com.fmillone.fci.config.JobCompletionNotificationListener
import com.fmillone.fci.fundStatus.TrustStatus
import com.fmillone.fci.fundStatus.TrustStatusReader
import com.fmillone.fci.fundStatus.remote.RemoteTrustStatusClient
import groovy.transform.CompileStatic
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDate

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
    RemoteTrustStatusClient remoteTrustStatusClient
    @Autowired
    JobLauncher jobLauncher

    void start() {
        jobLauncher.run(
                importTrustStatusJob(),
                new JobParameters()
        )
    }


    Job importTrustStatusJob() {
        return jobBuilderFactory.get("importTrustStatuses")
                .listener(jobCompletionNotificationListener)
                .flow(importFundStatusesStep)
                .end()
                .build()
    }

    TrustStatusReader getTrustStatusReader() {
            new TrustStatusReader(
                remoteTrustStatusClient: remoteTrustStatusClient,
                currentDate: LocalDate.parse('2017-01-02'),
                to: today
        )
    }

    Step getImportFundStatusesStep() {
        return stepBuilderFactory.get("import fund statuses")
                .<TrustStatus, TrustStatus> chunk(10)
                .reader(trustStatusReader)
                .writer(repositoryItemWriter)
                .build()
    }
}

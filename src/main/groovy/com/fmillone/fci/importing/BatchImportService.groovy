package com.fmillone.fci.importing

import com.fmillone.fci.config.JobCompletionNotificationListener
import com.fmillone.fci.fundStatus.TrustStatus
import com.fmillone.fci.importing.fundStatus.CNVFundService
import com.fmillone.fci.importing.fundStatus.RemoteFundStatusService
import com.fmillone.fci.importing.fundStatus.TrustStatusReader
import groovy.transform.CompileStatic
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.batch.item.support.CompositeItemProcessor
import org.springframework.batch.item.validator.ValidationException
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
    RemoteFundStatusService remoteFundStatusService
    @Autowired
    JobLauncher jobLauncher

    @Autowired
    CNVFundService cnvFundService

    JobExecution startImportFromCafci() {
        jobLauncher.run(
                importTrustStatusJob(),
                new JobParameters()
        )
    }

    JobExecution startImportFromCNV() {
        jobLauncher.run(importFromCNVJob(), new JobParameters())
    }

    Job importFromCNVJob() {
        jobBuilderFactory.get('importFromCNV')
                .flow(importFromCNVStep)
                .end()
                .build()
    }

    Step getImportFromCNVStep() {
        stepBuilderFactory.get('import from cnv step')
                .<String, List<TrustStatus>> chunk(10)
                .reader(fetchIdsReader)
                .processor(processor())
                .writer(fundStatusBatchWriter())
                .faultTolerant().skip(ValidationException).skipLimit(1000000)
                .build()
    }

    ItemWriter<List<TrustStatus>> fundStatusBatchWriter() {
        new FlattenListWriter(repositoryItemWriter)
    }

    ItemReader<String> getFetchIdsReader() {
        Map<String, String> ids = cnvFundService.lastPageIds()
        return new IdProvider(ids)
    }

    ItemProcessor<String, List<TrustStatus>> processor() {
        new CompositeItemProcessor<String, List<TrustStatus>>(delegates: [
                new FetchZipProcessor(cnvFundService),
                new UnzipProcessor(),
                new MapToFundStatus()
        ])
    }

    Job importTrustStatusJob() {
        return jobBuilderFactory.get('importTrustStatuses')
                .listener(jobCompletionNotificationListener)
                .flow(importFundStatusesStep)
                .end()
                .build()
    }

    TrustStatusReader getTrustStatusReader() {
        new TrustStatusReader(
                service: remoteFundStatusService,
                currentDate: LocalDate.parse('2017-01-02'),
                to: today
        )
    }

    Step getImportFundStatusesStep() {
        return stepBuilderFactory.get('import fund statuses')
                .<TrustStatus, TrustStatus> chunk(10)
                .reader(trustStatusReader)
                .writer(repositoryItemWriter)
                .build()
    }
}

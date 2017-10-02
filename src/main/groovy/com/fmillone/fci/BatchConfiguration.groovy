package com.fmillone.fci

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class BatchConfiguration {

    @Bean
    RepositoryItemWriter<TrustStatus> repositoryItemWriter(TrustStatusRepository trustStatusRepository){
        RepositoryItemWriter<TrustStatus> writer = new RepositoryItemWriter<>()
        writer.with {
            methodName = 'save'
            repository = trustStatusRepository
        }
        return writer
    }

}

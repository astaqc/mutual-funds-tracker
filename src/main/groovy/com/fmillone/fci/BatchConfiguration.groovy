package com.fmillone.fci

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor

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

    @Bean
    JobLauncher jobLauncher(TaskExecutor taskExecutor,
                            JobRepository jobRepository){
        new SimpleJobLauncher(
                taskExecutor: taskExecutor,
                jobRepository: jobRepository
        )
    }

    @Bean
    TaskExecutor taskExecutor(){
        new SimpleAsyncTaskExecutor()
    }

}

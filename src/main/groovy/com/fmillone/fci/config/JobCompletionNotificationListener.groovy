package com.fmillone.fci.config

import groovy.util.logging.Log
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
@Log
class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    public static final String JOB_FINISHED_MESSAGE = "!!! JOB FINISHED! Time to verify the results"

    @Override
    void afterJob(JobExecution jobExecution) {
        log.info jobExecution.status.name()
        if (jobExecution.status == BatchStatus.COMPLETED) {
            log.info JOB_FINISHED_MESSAGE
        }
    }
}
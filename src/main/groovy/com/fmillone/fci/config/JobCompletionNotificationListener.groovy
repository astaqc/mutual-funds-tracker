package com.fmillone.fci.config

import groovy.util.logging.Log4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
@Log4j
class JobCompletionNotificationListener extends JobExecutionListenerSupport {

//    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener)
    public static final String JOB_FINISHED_MESSAGE = "!!! JOB FINISHED! Time to verify the results"

    private final JdbcTemplate jdbcTemplate

    @Autowired
    JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate
    }

    @Override
    void afterJob(JobExecution jobExecution) {
        log.info jobExecution.status.name()
        if (jobExecution.status == BatchStatus.COMPLETED) {
            log.info JOB_FINISHED_MESSAGE
        }
    }
}
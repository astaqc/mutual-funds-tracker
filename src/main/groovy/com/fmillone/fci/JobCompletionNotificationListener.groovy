package com.fmillone.fci

import java.sql.ResultSet
import java.sql.SQLException
import java.util.List

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

@Component
class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener)

    private final JdbcTemplate jdbcTemplate

    @Autowired
    JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate
    }

    @Override
    void afterJob(JobExecution jobExecution) {
        log.info(jobExecution.status.name())
        if(jobExecution.status == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results")

//            List<TrustStatus> results = jdbcTemplate.query("SELECT first_name, last_name FROM people", new RowMapper<TrustStatus>() {
//                @Override
//                TrustStatus mapRow(ResultSet rs, int row) throws SQLException {
//                    return new TrustStatus(rs.getString(1), rs.getString(2))
//                }
//            })
//
//            for (TrustStatus person : results) {
//                log.info("Found <" + person + "> in the database.")
//            }

        }
    }
}
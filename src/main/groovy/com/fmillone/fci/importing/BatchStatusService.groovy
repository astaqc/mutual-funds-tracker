package com.fmillone.fci.importing

import groovy.transform.CompileStatic
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class BatchStatusService {

    @Autowired
    JobExplorer jobExplorer


    BatchStatus getStatus(String jobName){
        jobExplorer.findRunningJobExecutions(jobName)
                .first()?.status
    }

    JobExecution getJobExecution(String jobName){
        jobExplorer.findRunningJobExecutions(jobName)
                .first()
    }
}

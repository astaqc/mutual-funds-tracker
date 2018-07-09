package com.fmillone.fci.utils

import com.fmillone.fci.importing.CustomJobParameter
import groovy.transform.CompileStatic
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder

@CompileStatic
class BatchUtils {

    static <T> JobParameters parameters(Map<String,T> map){
        def builder = new JobParametersBuilder()
        map.each {
            builder.addParameter(it.key, new CustomJobParameter(it.value))
        }
        return builder.toJobParameters()
    }
}

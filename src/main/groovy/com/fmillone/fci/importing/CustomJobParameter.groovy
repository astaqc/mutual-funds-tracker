package com.fmillone.fci.importing

import groovy.transform.CompileStatic
import org.springframework.batch.core.JobParameter

@CompileStatic
class CustomJobParameter<T> extends JobParameter {
    private T customParam

    CustomJobParameter(T customParam){
        super(UUID.randomUUID().toString()) //This is to avoid duplicate JobInstance error
        this.customParam = customParam
    }

    T getValue(){
        return customParam
    }
}
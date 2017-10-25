package com.fmillone.fci.utils

import groovy.transform.CompileStatic

@CompileStatic
class NotFoundException extends RuntimeException{

    static NotFoundException newInstance(){
        return new NotFoundException()
    }
}

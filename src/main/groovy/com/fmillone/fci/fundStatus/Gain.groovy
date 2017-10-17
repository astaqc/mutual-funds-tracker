package com.fmillone.fci.fundStatus

import groovy.transform.CompileStatic


@CompileStatic
class Gain {

    final Double percentGain
    final Double total
    final Double realGain

    Gain(Double initial, Double percentGain) {
        this.percentGain = percentGain
        Double multiplier = (percentGain / 100.0).toDouble()
        this.total = initial * (1 + multiplier)
        this.realGain = initial * multiplier

    }

}

package com.fmillone.fci

import groovy.transform.EqualsAndHashCode

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@EqualsAndHashCode
class TrustStatus {

    @Id
    @GeneratedValue
    Long id
    String name
    String Horiz
    Date date
    Long amountOfPieces
    Long totalValue


    BigDecimal getValuesPerUnity() {
        null
    }

}

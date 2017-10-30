package com.fmillone.fci.fundStatus

import groovy.transform.EqualsAndHashCode

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import java.time.LocalDate

@Entity
@EqualsAndHashCode
class TrustStatus {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id
    String name
    String Horiz
    LocalDate date
    Long amountOfPieces
    Double totalValue
    Double valuesPerUnity


}

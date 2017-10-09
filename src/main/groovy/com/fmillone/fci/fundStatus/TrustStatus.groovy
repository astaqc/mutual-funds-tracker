package com.fmillone.fci.fundStatus

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
    Double valuesPerUnity


//    String getValuesPerUnity() {
//        def bigAmountofPieces = BigDecimal.valueOf(amountOfPieces)
//        return BigDecimal
//                .valueOf(totalValue)
//                .divide(
//                bigAmountofPieces,
//                5,
//                RoundingMode.HALF_EVEN
//        ).toString()
//    }

}

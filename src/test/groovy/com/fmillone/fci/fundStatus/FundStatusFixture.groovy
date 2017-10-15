package com.fmillone.fci.fundStatus

import java.time.LocalDate

class FundStatusFixture {

    static TrustStatus from(Map params = [:]){
        new TrustStatus(
                name: params.get('name', 'name'),
                Horiz: params.get('Horiz', 'P'),
                totalValue: params.get('totalValue', 1000L),
                amountOfPieces: params.get('amountOfPieces', 1000L),
                valuesPerUnity: params.get('valuesPerUnity', 10.5D),
                date: params.get('date', LocalDate.now())
        )
    }

}

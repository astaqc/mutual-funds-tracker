package com.fmillone.fci.fundStatus

import java.time.LocalDate

class FundStatusFixture {

    static TrustStatus from(Map params = [:]){
        new TrustStatus(
                name: params.get('name', 'name'),
                Horiz: params.get('Horiz', 'P'),
                totalValue: params.get('totalValue', 1000.0),
                amountOfPieces: params.get('amountOfPieces', 1000L),
                valuesPerUnity: params.get('valuesPerUnity', 10.5D),
                date: params.get('date', LocalDate.now())
        )
    }

    static TrustStatus getSomeFundStatus(){
        return from()
    }

    static TrustStatus getSomeOtherFundStatus(){
        return from(valuesPerUnity: 12.0D)
    }

}

package com.fmillone.fci.fundStatus

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.toDate

class FundStatusDTO {

    Long id
    String name
    String horiz
    Date date
    Long amountOfPieces
    Long totalValue
    Double unitaryValue
    String currency
    RentType rentType

    static FundStatusDTO from(TrustStatus trustStatus) {
        new FundStatusDTO(
                id: trustStatus.id,
                name: trustStatus.name,
                horiz: trustStatus.Horiz,
                date: extractDate(trustStatus),
                amountOfPieces: trustStatus.amountOfPieces,
                totalValue: trustStatus.totalValue,
                unitaryValue: trustStatus.unitaryValue,
                currency: trustStatus.currency,
                rentType: trustStatus.rentType
        )
    }


    private static Date extractDate(TrustStatus trustStatus) {
        LocalDate localDate = trustStatus.date
        return localDate ? toDate(localDate) : null
    }

}

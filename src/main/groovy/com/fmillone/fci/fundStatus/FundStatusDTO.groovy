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
    Double valuesPerUnity

    static FundStatusDTO from(TrustStatus trustStatus) {
        new FundStatusDTO(
                id: trustStatus.id,
                name: trustStatus.name,
                horiz: trustStatus.Horiz,
                date: extractDate(trustStatus),
                amountOfPieces: trustStatus.amountOfPieces,
                totalValue: trustStatus.totalValue,
                valuesPerUnity: trustStatus.valuesPerUnity
        )
    }


    private static Date extractDate(TrustStatus trustStatus) {
        LocalDate localDate = trustStatus.date
        return localDate ? toDate(localDate) : null
    }

}

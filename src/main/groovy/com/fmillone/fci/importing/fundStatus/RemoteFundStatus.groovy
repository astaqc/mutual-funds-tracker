package com.fmillone.fci.importing.fundStatus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fmillone.fci.fundStatus.RentType
import com.fmillone.fci.fundStatus.TrustStatus
import groovy.transform.CompileStatic

import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
@CompileStatic
class RemoteFundStatus {

    String fondo
    Double patrimonio
    String horizonte
    Long cpp
    Double vcp

    boolean isValid() {
        horizonte != null
    }

    TrustStatus toTrustStatus(LocalDate localDate, RentType rentType) {
        new TrustStatus(
                date: localDate,
                name: fondo,
                Horiz: horizonte,
                totalValue: patrimonio,
                unitaryValue: vcp,
                amountOfPieces: cpp,
                rentType: rentType
        )
    }
}

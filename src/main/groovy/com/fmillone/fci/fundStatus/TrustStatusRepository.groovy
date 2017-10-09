package com.fmillone.fci.fundStatus

import org.springframework.data.repository.CrudRepository

interface TrustStatusRepository  extends CrudRepository<TrustStatus, Long> {

    Optional<TrustStatus> save(TrustStatus status)
    Optional<List<TrustStatus>> findAllByDateGreaterThan(Date date)

}

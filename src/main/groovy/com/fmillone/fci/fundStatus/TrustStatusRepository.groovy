package com.fmillone.fci.fundStatus

import org.springframework.data.repository.CrudRepository

import java.time.LocalDate

interface TrustStatusRepository  extends CrudRepository<TrustStatus, Long> {

    Optional<TrustStatus> save(TrustStatus status)
    List<TrustStatus> findAllByDateGreaterThan(LocalDate date)
    Optional<TrustStatus> findByNameAndDate(String name, LocalDate date)


}

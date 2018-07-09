package com.fmillone.fci.fundStatus

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.time.LocalDate

@Repository
interface TrustStatusRepository extends CrudRepository<TrustStatus, Long> {

    Optional<TrustStatus> save(TrustStatus status)

    List<TrustStatus> findAllByDateGreaterThan(LocalDate date)

    Optional<TrustStatus> findByNameAndDate(String name, LocalDate date)


}

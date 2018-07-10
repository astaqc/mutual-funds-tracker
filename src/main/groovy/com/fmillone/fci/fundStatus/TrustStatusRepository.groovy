package com.fmillone.fci.fundStatus

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.time.LocalDate

@Repository
interface TrustStatusRepository extends JpaRepository<TrustStatus, Long> {

    Optional<TrustStatus> save(TrustStatus status)

    List<TrustStatus> findAllByDateGreaterThan(LocalDate date)

    Optional<TrustStatus> findByNameAndDate(String name, LocalDate date)


}

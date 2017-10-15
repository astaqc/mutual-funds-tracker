package com.fmillone.fci.fundStatus

import com.fmillone.fci.FciApplication
import org.hibernate.SessionFactory
import org.hibernate.StatelessSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDate

import static com.fmillone.fci.fundStatus.FundStatusFixture.from
import static com.fmillone.fci.utils.DateUtils.today

@SpringBootTest(classes = [FciApplication])
@Transactional
class TrustStatusRepositoryISpec extends Specification {

    @Autowired
    TrustStatusRepository repository

    @Autowired
    SessionFactory sessionFactory

    void setup() {

    }

    void cleanup() {
        StatelessSession session = sessionFactory.openStatelessSession()
        session.createQuery('delete from TrustStatus')
                .executeUpdate()
        session.close()
    }

    void "it should save a fund status"() {
        given:
            TrustStatus trustStatus = new TrustStatus(
                    date: today,
                    name: 'name',
                    Horiz: 'P',
                    amountOfPieces: 1000L,
                    totalValue: 1000L,
                    valuesPerUnity: 10.5D
            )
        when:
            TrustStatus saved = repository.save(trustStatus).get()
        then:
            saved != null
            saved.id != null
            saved.date.toString() == today.toString()
            saved.name == 'name'
            saved.Horiz == 'P'
            saved.amountOfPieces == 1000L
            saved.totalValue == 1000L
            saved.valuesPerUnity == 10.5D

    }

    void "it should find all the fund statuses since a date"() {
        given:
            (0..3).each {
                repository.save(from(date: today.minusDays(it)))
            }
            LocalDate treeDaysAgo = today.minusDays(3)
        when:
            Optional<List> optional = repository.findAllByDateGreaterThan(treeDaysAgo)
        then:
            optional.isPresent()
            List fundStatuses = optional.get()
            fundStatuses.size() == 3
    }
}


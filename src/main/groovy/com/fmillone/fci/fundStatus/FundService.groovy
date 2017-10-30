package com.fmillone.fci.fundStatus

import com.fmillone.fci.utils.NotFoundException
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDate

import static com.fmillone.fci.utils.DateUtils.*

@Service
@Transactional
@CompileStatic
class FundService {

    @Autowired
    TrustStatusRepository repository


    List<TrustStatus> getAllByDateGreaterThan(LocalDate date) {
        repository.findAllByDateGreaterThan(date)
    }

    Gain calculateGain(String name, Double start, LocalDate since, LocalDate to = today) {
        TrustStatus old = getByNameAndNearDate(name, since)
        TrustStatus last = getByNameAndNearDate(name, to)

        new Gain(start, getPercentGain(old, last))
    }

    private static double getPercentGain(TrustStatus old, TrustStatus last) {
        (last.valuesPerUnity - old.valuesPerUnity) * 100 / old.valuesPerUnity
    }


    TrustStatus getByNameAndNearDate(String name, LocalDate date) {
        date = isWeekend(date) ? previousWeekday(date) : date
        return repository
                .findByNameAndDate(name, date)
                .orElseThrow(NotFoundException.&newInstance)
    }

}

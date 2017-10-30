package com.fmillone.fci.importing.fundStatus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDate

import static com.fmillone.fci.importing.fundStatus.RemoteFundStatusRestClient.VARIABLE_RENT

@Service
class RemoteFundStatusService implements RemoteFundStatusClient {

    @Autowired
    RemoteFundStatusRestClient client

    @Override
    List<RemoteFundStatus> fetchByTypeAndDate(int type = VARIABLE_RENT,LocalDate date) {
        client.fetch(type, date)
                .execute()
                .body()
                ?.data ?: []
    }
}

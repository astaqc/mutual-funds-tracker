package com.fmillone.fci.importing.fundStatus


import java.time.LocalDate

interface RemoteFundStatusClient {

    Object fetchByTypeAndDate(int type, LocalDate date)
}
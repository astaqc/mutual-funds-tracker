package com.fmillone.fci.importing.fundStatus

import groovy.transform.CompileStatic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

import java.time.LocalDate

@CompileStatic
interface RemoteFundStatusRestClient {

    static final int VARIABLE_RENT = 2

    @GET('estadisticas/informacion/diaria/{type}/{date}')
    Call<AllFundStatusResponse> fetch(@Path('type') type, @Path('date') LocalDate date)
}

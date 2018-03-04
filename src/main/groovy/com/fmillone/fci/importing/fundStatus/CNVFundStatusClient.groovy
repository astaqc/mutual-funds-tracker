package com.fmillone.fci.importing.fundStatus

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CNVFundStatusClient {

    @GET('InfoFinan/Fondos/Zips.asp?CodiSoc=70086&Tipoarchivo=1&TipoDocum=26')
    Call<ResponseBody> fetchLastPage()

    @GET('Infofinan/fondos/BLOB_Zip.asp?error_page=Error.asp')
    Call<ResponseBody> downloadFile(@Query('cod_doc') String codDoc)
}
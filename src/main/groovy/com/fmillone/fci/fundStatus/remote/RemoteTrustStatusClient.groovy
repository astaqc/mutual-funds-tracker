package com.fmillone.fci.fundStatus.remote

import com.fmillone.fci.fundStatus.TrustStatusUtils
import groovy.util.slurpersupport.GPathResult
import okhttp3.*
import org.springframework.stereotype.Service
import org.springframework.web.util.UriUtils

import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@Service
class RemoteTrustStatusClient {

    OkHttpClient client

    RemoteTrustStatusClient() {
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    GPathResult fetch(LocalDate date) {
        setDate(date)
        Response response = fetchStatus()
        String body = response.body().string()
        response.close()
        new XmlSlurper().parseText(body)
    }


    private Response execute(Request request) {
        client.newCall(request).execute()
    }

    private Response fetchStatus() {
        Request request = buildRequest('cfn_PlanillaDiariaXMLList.asp')
        return execute(request)
    }

    private Request buildRequest(String service, RequestBody body = null) {
        new Request.Builder()
                .url("http://www.cafci.org.ar/Scripts/$service")
                .post(body ?: emptyBody)
                .addHeader('content-type', 'application/x-www-form-urlencoded')
                .addHeader('cache-control', 'no-cache')
                .build()
    }

    private static RequestBody getEmptyBody() {
        return buildBody()
    }

    private static RequestBody buildBody(String content = 'query=1') {
        MediaType mediaType = MediaType.parse('application/x-www-form-urlencoded')
        RequestBody.create(mediaType, content)
    }

    void executeSetDate(String formattedDate) {
        Request request = buildRequest(
                'cfn_EstadisticaVCPXMLSet.asp',
                buildBody(buildFormBody(formattedDate))
        )

        execute(request).close()
    }

    static String buildFormBody(String formattedDate) {
        'query=' + UriUtils.encode(buildQuery(formattedDate), 'UTF-8')
    }


    static DateTimeFormatter getFormatter() {
        TrustStatusUtils.dateFormatter
    }

    private void setDate(LocalDate date) {
        String formattedDate = formatter.format(date)
        executeSetDate(formattedDate)
    }

    private static String buildQuery(String formattedDate) {
        """
        <Coleccion>
            <Parametros>
                <TRentaI>1</TRentaI>
                <TRentaN>Renta Variable</TRentaN>
                <Fecha>${formattedDate}</Fecha>
                <Separador>P</Separador>
            </Parametros>
        </Coleccion>""".replaceAll(/\n\s+/, '')
    }
}
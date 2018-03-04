package com.fmillone.fci.config

import com.fmillone.fci.importing.fundStatus.CNVFundStatusClient
import groovy.transform.CompileStatic
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY

@CompileStatic
@Configuration
class CNVClientConfig {

    @Value('${cnv.api.url}')
    final String cnvBaseUrl

    @Bean
    CNVFundStatusClient cnvFundStatusClient() {
        new Retrofit.Builder()
                .baseUrl("http://$cnvBaseUrl/")
                .client(client)
                .build()
                .create(CNVFundStatusClient)
    }

    @Bean
    OkHttpClient getClient() {
        new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(level: BODY))
                .build()
    }


}

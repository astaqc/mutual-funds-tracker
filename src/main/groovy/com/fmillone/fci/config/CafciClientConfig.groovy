package com.fmillone.fci.config

import com.fmillone.fci.importing.fundStatus.RemoteFundStatusRestClient
import groovy.transform.CompileStatic
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

import static okhttp3.logging.HttpLoggingInterceptor.Level.*

@CompileStatic
@Configuration
class CafciClientConfig {

    @Value('http://${cafci.api.url}/')
    final String cafciBaseUrl

    @Bean
    RemoteFundStatusRestClient remoteFundStatusRestClient() {
        new Retrofit.Builder()
                .baseUrl(cafciBaseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(RemoteFundStatusRestClient)
    }

    private static OkHttpClient getClient() {
        new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(level: BODY))
                .build()
    }


}

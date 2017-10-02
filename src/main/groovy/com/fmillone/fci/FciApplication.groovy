package com.fmillone.fci

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableBatchProcessing
class FciApplication {

    static void main(String[] args) {
        SpringApplication.run FciApplication, args
    }

}

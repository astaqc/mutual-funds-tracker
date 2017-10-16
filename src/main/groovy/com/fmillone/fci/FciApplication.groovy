package com.fmillone.fci

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableBatchProcessing
@EnableSwagger2
class FciApplication {

    static void main(String[] args) {
        SpringApplication.run FciApplication, args
    }

}

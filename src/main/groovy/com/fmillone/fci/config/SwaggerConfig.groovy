package com.fmillone.fci.config

import com.fasterxml.classmate.TypeResolver
import com.google.common.base.Predicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.async.DeferredResult
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.AlternateTypeRule
import springfox.documentation.schema.WildcardType
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

import java.time.LocalDate

import static com.google.common.base.Predicates.or
import static springfox.documentation.builders.PathSelectors.regex
import static springfox.documentation.schema.AlternateTypeRules.newRule

@Configuration
class SwaggerConfig {

    @Bean
    Docket fciApi() {
        new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths)
                .build()
                .apiInfo(apiInfo)
                .pathMapping("/")
                .directModelSubstitute(LocalDate, String)
                .genericModelSubstitutes(ResponseEntity)
                .alternateTypeRules(alternateRule)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(true)

    }

    private static Predicate<String> getPaths() {
        or regex('/api.*'),
                regex('/startJob')
    }

    private AlternateTypeRule getAlternateRule() {
        newRule typeResolver.resolve(DeferredResult,
                typeResolver.resolve(ResponseEntity, WildcardType)),
                typeResolver.resolve(WildcardType)
    }


    private static ApiInfo getApiInfo() {
        ['FCI Api',
         'description',
         '1.0',
         '',
         new Contact('Me', '', 'me@me.com'),
         'licence',
         'licence url',
         []
        ] as ApiInfo
    }

    @Autowired
    private TypeResolver typeResolver

}

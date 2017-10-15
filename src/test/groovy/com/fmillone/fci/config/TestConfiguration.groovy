package com.fmillone.fci.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean

@Configuration
class TestConfiguration {

    @Bean
    HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean()
    }
}

package com.fmillone.fci

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest(classes = [FciApplication])
@EnableJpaRepositories
class FciApplicationTests {

    @Test
    void contextLoads() {
    }

}

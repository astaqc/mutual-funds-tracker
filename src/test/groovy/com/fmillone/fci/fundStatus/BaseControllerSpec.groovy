package com.fmillone.fci.fundStatus

import groovy.json.JsonSlurper
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

abstract class BaseControllerSpec extends Specification {

    MockMvc mockMvc

    void setupMockMvc(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def getJsonResponse(MockHttpServletResponse response) {
        new JsonSlurper().parseText(response.contentAsString)
    }

    MockHttpServletResponse performGET(String urlTemplate) {
        return mockMvc
                .perform(get(urlTemplate))
                .andReturn()
                .response
    }


}
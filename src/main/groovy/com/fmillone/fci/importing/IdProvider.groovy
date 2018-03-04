package com.fmillone.fci.importing

import groovy.transform.CompileStatic
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException

@CompileStatic
class IdProvider implements ItemReader<String> {

    Iterator<Map.Entry<String,String>> iterator

    IdProvider(Map<String, String> ids) {
        iterator = ids.iterator()
    }

    @Override
    String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(iterator==null || !iterator.hasNext()){
            return null
        }

        return iterator.next().value
    }
}

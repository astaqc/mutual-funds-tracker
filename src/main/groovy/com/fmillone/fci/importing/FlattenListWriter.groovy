package com.fmillone.fci.importing

import groovy.transform.CompileStatic
import org.springframework.batch.item.ItemWriter

import static java.util.stream.Collectors.toList

@CompileStatic
class FlattenListWriter<T> implements ItemWriter<List<T>> {

    ItemWriter<T> delegate

    FlattenListWriter(ItemWriter<T> delegate) {
        this.delegate = delegate
    }

    @Override
    void write(List<? extends List<T>> items) throws Exception {
        delegate.write(flattened(items))
    }

    private List<T> flattened(List<? extends List<T>> items) {
        items.stream()
                .flatMap({ it.stream() })
                .collect(toList())
    }
}

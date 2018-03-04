package com.fmillone.fci.importing

import groovy.transform.CompileStatic
import org.springframework.batch.item.ItemProcessor

import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

@CompileStatic
class UnzipProcessor implements ItemProcessor<InputStream, InputStream> {

    @Override
    InputStream process(InputStream item) throws Exception {
        ZipInputStream zipInput = new ZipInputStream(item)
        selectEntry(zipInput)
        return new ByteArrayInputStream(zipInput.bytes)
    }

    private void selectEntry(ZipInputStream zipInput) {
        while (!isTheRightEntry(zipInput.nextEntry)) {
        } //I know, I know
    }

    private boolean isTheRightEntry(ZipEntry entry) {
        entry != null && isXls(entry.name)
    }

    private boolean isXls(String name) {
        name.endsWith('.xls') || name.endsWith('.XLS')
    }
}

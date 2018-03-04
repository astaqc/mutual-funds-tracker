package com.fmillone.fci.importing

import com.fmillone.fci.importing.fundStatus.CNVFundService
import org.springframework.batch.item.ItemProcessor

class FetchZipProcessor implements ItemProcessor<String, InputStream> {

    CNVFundService cnvFundService

    FetchZipProcessor(CNVFundService cnvFundService) {
        this.cnvFundService = cnvFundService
    }

    @Override
    InputStream process(String id) throws Exception {
        return cnvFundService.downloadFile(id)
    }
}

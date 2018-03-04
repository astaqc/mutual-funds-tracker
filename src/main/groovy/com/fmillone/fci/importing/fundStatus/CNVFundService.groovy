package com.fmillone.fci.importing.fundStatus

import groovy.util.slurpersupport.GPathResult
import okhttp3.ResponseBody
import org.ccil.cowan.tagsoup.Parser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import retrofit2.Response

@Service
class CNVFundService {

    @Autowired
    CNVFundStatusClient cnvFundStatusClient

    Map<String, String> lastPageIds() {
        GPathResult rawData = fetchRawData()
        rawData.'**'.findAll {
            it.name() == 'a' && it.@href.text().startsWith('/Infofinan/fondos/BLOB_Zip.asp')
        }.collectEntries {
            [(it.text()): (it.@href.text() =~ /cod_doc=(\d+)/)[0][1]]
        }
    }

    private GPathResult fetchRawData() {
        def text = cnvFundStatusClient.fetchLastPage().execute().body().string()
        slurper.parseText(text)
    }

    XmlSlurper getSlurper() {
        new XmlSlurper(new Parser())
    }

    InputStream downloadFile(String id) {
        Response<ResponseBody> response = cnvFundStatusClient.downloadFile(id).execute()
        if(response.isSuccessful()){
            return response.body().byteStream()
        }
    }
}

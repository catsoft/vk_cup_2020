package com.catsoft.vktinE.vkApi.documents.requests

import com.catsoft.vktinE.vkApi.documents.model.VKApiDocument
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetDocumentListRequest: VKRequest<List<VKApiDocument>>("docs.get") {

    init {
        addParam("return_tags", 1)
    }

    override fun parse(r: JSONObject): List<VKApiDocument> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKApiDocument>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKApiDocument.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}


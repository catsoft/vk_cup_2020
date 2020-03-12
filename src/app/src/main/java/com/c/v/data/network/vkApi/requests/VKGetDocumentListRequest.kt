package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKDocument
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetDocumentListRequest: VKRequest<List<VKDocument>>("docs.get") {

    init {
        addParam("return_tags", 1)
    }

    override fun parse(r: JSONObject): List<VKDocument> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKDocument>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKDocument.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}


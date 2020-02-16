package com.catsoft.vktinA.vkApi.documents.requests

import com.catsoft.vktinA.vkApi.documents.model.VKApiDocument
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKDocumentListRequest: VKRequest<List<VKApiDocument>>("docs.get") {

    override fun parse(r: JSONObject): List<VKApiDocument> {
        val users = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKApiDocument>()
        for (i in 0 until users.length()) {
            result.add(
                VKApiDocument.parse(
                    users.getJSONObject(i)
                )
            )
        }
        return result
    }
}


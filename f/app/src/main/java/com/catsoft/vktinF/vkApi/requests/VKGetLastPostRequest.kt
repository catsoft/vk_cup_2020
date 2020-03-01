package com.catsoft.vktinF.vkApi.requests

import com.catsoft.vktinF.vkApi.model.VKPost
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetLastPostRequest(owner : Int): VKRequest<VKPost?>("wall.get") {

    init {
        addParam("owner_id", -owner)
        addParam("count", 1)
    }

    override fun parse(r: JSONObject): VKPost? {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKPost>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKPost.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result.firstOrNull()
    }
}
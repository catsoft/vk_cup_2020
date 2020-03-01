package com.catsoft.vktinG.vkApi.requests

import com.catsoft.vktinG.vkApi.model.VKGroup
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetGroupListRequest(userId : Int): VKRequest<List<VKGroup>>("groups.get") {

    init {
        addParam("user_id", userId)
        addParam("extended", 1)
        addParam("fields", listOf("market", "city").joinToString(","))
    }

    override fun parse(r: JSONObject): List<VKGroup> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKGroup>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKGroup.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}



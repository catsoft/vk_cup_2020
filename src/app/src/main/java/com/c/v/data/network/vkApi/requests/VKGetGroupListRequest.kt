package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKGroupApi
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetGroupListRequest(userId : Int): VKRequest<List<VKGroupApi>>("groups.get") {

    init {
        addParam("user_id", userId)
        addParam("extended", 1)
        addParam("fields", listOf("status", "is_hidden_from_feed", "description", "members_count").joinToString(","))
    }

    override fun parse(r: JSONObject): List<VKGroupApi> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKGroupApi>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKGroupApi.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}



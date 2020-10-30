package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKPost
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetAllPostsInWallRequest(owner : Int): VKRequest<List<VKPost>>("wall.get") {

    init {
        addParam("owner_id", owner)
        addParam("count", 100)
    }

    override fun parse(r: JSONObject): List<VKPost> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKPost>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKPost.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result.toList()
    }
}
package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKPost
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetPostsInWallRequest(owner : Int, count: Int = 100): VKRequest<List<VKPost>>("wall.get") {

    init {
        addParam("owner_id", owner)
        addParam("count", count)
    }

    override fun parse(r: JSONObject): List<VKPost> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val sType = object : TypeToken<List<VKPost>>() { }.type

        return  Gson().fromJson(jsonDocuments.toString(), sType)
    }
}
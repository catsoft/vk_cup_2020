package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetFriendsListRequest(userId : Int = 0): VKRequest<List<VKUser>>("friends.get") {

    init {
        addParam("user_id", userId)
        addParam("fields", listOf("photo_100").joinToString(","))
    }

    override fun parse(r: JSONObject): List<VKUser> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")

        val sType = object : TypeToken<List<VKUser>>() { }.type

        return  Gson().fromJson(jsonDocuments.toString(), sType)
    }
}



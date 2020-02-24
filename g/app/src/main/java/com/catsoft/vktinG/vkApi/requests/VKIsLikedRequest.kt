package com.catsoft.vktinG.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKIsLikedRequest(ownerId: Int, idProduct: Int): VKRequest<Boolean>("likes.isLiked") {

    init {
        addParam("item_id", idProduct)
        addParam("owner_id", ownerId)
        addParam("type", "market")
    }

    override fun parse(r: JSONObject): Boolean {
        val response = r.optJSONObject("response")
        val liked = response!!.optInt("liked")
        return liked == 1
    }
}
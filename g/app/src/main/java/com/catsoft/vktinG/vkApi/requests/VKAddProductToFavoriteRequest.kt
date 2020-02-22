package com.catsoft.vktinG.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKAddProductToFavoriteRequest(ownerId: Int, idProduct: Int): VKRequest<Int>("likes.add") {

    init {
        addParam("item_id", idProduct)
        addParam("owner_id", ownerId)
        addParam("type","market")
    }

    override fun parse(r: JSONObject): Int {
        return r.optInt("likes")
    }
}



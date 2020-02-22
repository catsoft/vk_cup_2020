package com.catsoft.vktinG.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKRemoveProductFromFavoriteRequest(ownerId: Int, productId : Int): VKRequest<Int>("likes.delete") {

    init {
        addParam("item_id", productId)
        addParam("type","market")
        addParam("owner_id", ownerId)
    }

    override fun parse(r: JSONObject): Int {
        return r.optInt("likes")
    }
}
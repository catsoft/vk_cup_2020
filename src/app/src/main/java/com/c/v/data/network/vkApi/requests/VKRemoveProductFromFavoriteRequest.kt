package com.c.v.data.network.vkApi.requests

import org.json.JSONObject

class VKRemoveProductFromFavoriteRequest(ownerId: Int, productId : Int): VKBaseRequest<Int>("fave.removeProduct") {

    init {
        addParam("id", productId)
        addParam("owner_id", ownerId)
    }

    override fun parse(r: JSONObject): Int {
        return 1
    }
}
package com.catsoft.vktinG.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKAddProductToFavoriteRequest(ownerId: Int, idProduct: Int): VKBaseRequest<Int>("fave.addProduct") {

    init {
        addParam("id", idProduct)
        addParam("owner_id", ownerId)
    }

    override fun parse(r: JSONObject): Int {
        return 1
    }
}
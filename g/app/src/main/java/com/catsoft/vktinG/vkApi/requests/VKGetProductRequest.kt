package com.catsoft.vktinG.vkApi.requests

import com.catsoft.vktinG.vkApi.model.VKProduct
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetProductRequest(owner : Int, itemId  : Int): VKBaseRequest<VKProduct>("market.getById") {

    init {
        addParam("item_ids", "${owner}_$itemId")
    }

    override fun parse(r: JSONObject): VKProduct {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKProduct>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKProduct.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result.first()
    }
}
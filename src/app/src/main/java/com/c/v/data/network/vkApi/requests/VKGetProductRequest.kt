package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKProduct
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
package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKProduct
import org.json.JSONObject

class VKGetProductListRequest(owner : Int): VKBaseRequest<List<VKProduct>>("market.get") {

    init {
        addParam("owner_id", -owner)
        addParam("count", 200)
    }

    override fun parse(r: JSONObject): List<VKProduct> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKProduct>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKProduct.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}
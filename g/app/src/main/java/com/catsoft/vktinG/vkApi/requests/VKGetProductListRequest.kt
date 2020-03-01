package com.catsoft.vktinG.vkApi.requests

import com.catsoft.vktinG.vkApi.model.VKProduct
import com.vk.api.sdk.requests.VKRequest
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
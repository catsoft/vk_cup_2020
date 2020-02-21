package com.catsoft.vktinG.vkApi.requests

import com.catsoft.vktinG.vkApi.model.VKGroup
import com.catsoft.vktinG.vkApi.model.VKProduct
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetGroupListRequest(userId : Int): VKRequest<List<VKGroup>>("groups.get") {

    init {
        addParam("user_id", userId)
        addParam("extended", 1)
        addParam("fields", listOf("market", "city").joinToString(","))
    }

    override fun parse(r: JSONObject): List<VKGroup> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKGroup>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKGroup.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}

class VKGetProductListRequest(owner : Int): VKRequest<List<VKProduct>>("market.get") {

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

package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKGroupApi
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKSearchGroupListRequest(cityId: Int): VKRequest<List<VKGroupApi>>("groups.search") {

    init {
        addParam("country_id", 1)
        if (cityId != -1) {
            addParam("city_id", cityId)
        }
        addParam("q", " ")
        addParam("market", 1)
        addParam("count", 200)
    }

    override fun parse(r: JSONObject): List<VKGroupApi> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKGroupApi>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKGroupApi.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}



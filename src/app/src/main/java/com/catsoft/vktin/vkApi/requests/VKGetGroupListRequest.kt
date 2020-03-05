package com.catsoft.vktin.vkApi.requests

import com.catsoft.vktin.vkApi.model.VKGroup
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGetGroupListRequest(cityId: Int): VKRequest<List<VKGroup>>("groups.search") {

    init {
        addParam("country_id", 1)
        if (cityId != -1) {
            addParam("city_id", cityId)
        }
        addParam("q", " ")
        addParam("market", 1)
        addParam("count", 200)
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



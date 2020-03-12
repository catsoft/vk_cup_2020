package com.c.v.data.network.vkApi.requests

import com.c.v.data.network.vkApi.model.VKCity
import org.json.JSONObject

class VKGetCityListRequest(): VKBaseRequest<List<VKCity>>("database.getCities") {

    init {
        addParam("country_id", 1)
        addParam("need_all", 0)
        addParam("count", 500)
    }

    override fun parse(r: JSONObject): List<VKCity> {
        val jsonDocuments = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKCity>()
        for (i in 0 until jsonDocuments.length()) {
            result.add(
                VKCity.parse(
                    jsonDocuments.getJSONObject(i)
                )
            )
        }
        return result
    }
}



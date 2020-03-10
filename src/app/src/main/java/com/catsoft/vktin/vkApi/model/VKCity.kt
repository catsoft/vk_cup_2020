package com.catsoft.vktin.vkApi.model

import org.json.JSONObject

data class VKCity(override val id: Int, val title: String) : IWithIdModel {
    companion object {
        fun parse(json: JSONObject?): VKCity {
            if (json == null) return VKCity(0, "")
            return VKCity(
                id = json.optInt("id", 0), title = json.optString("title", "")
            )
        }
    }
}
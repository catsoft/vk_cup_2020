package com.catsoft.vktinG.vkApi.model

import org.json.JSONObject

data class VKCity(val id: Int, val title: String) {
    companion object {
        fun parse(json: JSONObject?): VKCity {
            if (json == null) return VKCity(0, "")
            return VKCity(
                id = json.optInt("id", 0), title = json.optString("title", "")
            )
        }
    }
}
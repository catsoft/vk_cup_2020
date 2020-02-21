package com.catsoft.vktinG.vkApi.model

import org.json.JSONObject

data class VKMarket(
    val enabled : Boolean,
    val priceMin : Int,
    val priceMax : Int,
    val mainAlbumId : Int,
    val contactId : Int,
    val currencyText : String) {
    companion object {
        fun parse(json: JSONObject?): VKMarket {
            if(json == null) return VKMarket(false, 0, 0, 0, 0, "")
            return VKMarket(
                enabled = json.optInt("enabled") == 1,
                priceMax = json.optInt("price_max", 0),
                priceMin = json.optInt("price_min", 0),
                mainAlbumId = json.optInt("main_album_id", 0),
                contactId = json.optInt("contact_id", 0),
                currencyText = json.optString("currency_text", "Нет значения?")
            )
        }
    }
}


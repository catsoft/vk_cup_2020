package com.catsoft.vktinG.vkApi.model

import org.json.JSONObject

data class VKPrice(
    val amount : Float,
    val currency: VKCurrency,
    val text : String
) {
    companion object {
        fun parse(json: JSONObject?) : VKPrice {
            if (json == null) return VKPrice(0F, VKCurrency(0,"RU"), "")
            return VKPrice(
                amount = json.optInt("amount") / 100F,
                text = json.optString("text"),
                currency = VKCurrency.parse(json.optJSONObject("currency"))
            )
        }
    }
}

data class VKCurrency(
    val id : Int,
    val name : String
) {
    companion object {
        fun parse(json: JSONObject?): VKCurrency {
            if (json == null) return VKCurrency(0, "")
            return VKCurrency(
                id = json.optInt("id"), name = json.optString("name")
            )
        }
    }
}
package com.catsoft.vktinG.vkApi.model

import org.json.JSONObject

data class VKGroup (
    val id : Int,
    val name : String,
    val screenName : String,
    val deactivated : String,
    val isMember : Boolean,
    val isClosed : Int,
    val photo50 : String,
    val photo100 : String,
    val photo200 : String,
    val city : VKCity,
    val market : VKMarket) {

    companion object {

        fun parse(json: JSONObject): VKGroup {
            return VKGroup(
                id = json.optInt("id"),
                name = json.optString("name"),
                screenName = json.optString("screen_name"),
                deactivated = json.optString("deactivated"),
                isClosed = json.optInt("is_closed"),
                isMember = json.optInt("is_member") == 1,
                photo50 = json.optString("photo_50"),
                photo100 = json.optString("photo_100"),
                photo200 = json.optString("photo_200"),
                city = VKCity.parse(json.optJSONObject("city")),
                market = VKMarket.parse(json.optJSONObject("market"))
            )
        }
    }
}

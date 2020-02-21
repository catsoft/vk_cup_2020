package com.catsoft.vktinG.vkApi.model

import org.json.JSONObject

data class VKProduct(
    val id : Int,
    val ownerId : Int,
    val title : String,
    val description : String,
    val price : VKPrice,
//    val category : VKCategory
    val thumb_photo : String,
//    val date : Int,
    val availability : Int,
    val isFavorite : Boolean
)
{
    companion object {
        fun parse(json: JSONObject?): VKProduct {
            if(json == null) return VKProduct(0, 0, "", "", VKPrice(0F, VKCurrency(0, "RU"),""), "", 0, false)
            return VKProduct(
                id = json.optInt("id"),
                ownerId = json.optInt("owner_id"),
                title = json.optString("title"),
                description = json.optString("description"),
                price = VKPrice.parse(json.optJSONObject("price")),
                thumb_photo = json.optString("thumb_photo"),
                availability = json.optInt("availability"),
                isFavorite = json.optBoolean("is_favorite")
            )
        }
    }
}
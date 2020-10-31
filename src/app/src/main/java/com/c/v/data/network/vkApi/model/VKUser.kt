package com.c.v.data.network.vkApi.model

import com.google.gson.annotations.SerializedName

data class VKUser(
    @SerializedName("id")
    val id: Int,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("deactivated")
    val deactivated: String,

    @SerializedName("is_closed")
    val isClosed: Boolean,

    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,

    @SerializedName("photo_100")
    val photo100: String?
)
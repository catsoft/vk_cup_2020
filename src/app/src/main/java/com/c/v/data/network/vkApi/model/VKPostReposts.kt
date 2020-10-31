package com.c.v.data.network.vkApi.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VKPostReposts(

    @SerializedName("count")
    val count: Int

) : Serializable
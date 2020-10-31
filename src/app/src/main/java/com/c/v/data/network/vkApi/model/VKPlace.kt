package com.c.v.data.network.vkApi.model

import com.c.v.data.IWithIdModel
import java.io.Serializable

data class VKPlace(
    override val id: Int,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val created: Int,
    val icon: String?,
    val checkins: Int,
    val updated: Int,
    val type: Int,
    val country: String?,
    val city: String?,
    val address: String?
) : Serializable, IWithIdModel


package com.c.v.data.network.vkApi.model

import com.c.v.data.IWithIdModel

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
    val country: Int,
    val city: Int,
    val address: String
) : IWithIdModel


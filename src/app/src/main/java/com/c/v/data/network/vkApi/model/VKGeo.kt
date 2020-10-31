package com.c.v.data.network.vkApi.model

import java.io.Serializable

data class VKGeo(val type: String, val coordinates: String, val place: VKPlace?) : Serializable
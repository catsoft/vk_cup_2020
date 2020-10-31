package com.c.v.ui.check_in_flow.places.dto

import com.c.v.data.IWithIdModel
import com.c.v.data.network.vkApi.model.VKGeo

data class PlacePresentationDto(
    override val id: Int,
    val title: String,
    val subtitle: String,
    val icon: String,
    val latitude: Double,
    val longitude: Double

) : IWithIdModel {
    companion object {
        fun fromVKGeo(geo: VKGeo) : PlacePresentationDto {
            val subtitle = geo.place!!.checkins.toString() + " " + geo.place.address
            val icon = geo.place.icon.orEmpty()
            val (latitude, longitude) = geo.coordinates.split(" ").map { it.toDouble() }
            return PlacePresentationDto(geo.place.id, geo.place.title, subtitle, icon, latitude, longitude)
        }
    }
}

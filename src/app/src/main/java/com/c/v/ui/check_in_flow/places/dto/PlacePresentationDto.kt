package com.c.v.ui.check_in_flow.places.dto

import com.c.v.data.IWithIdModel
import com.c.v.data.network.vkApi.model.VKPlace

data class PlacePresentationDto(
    override val id: Int,
    val title: String,
    val subtitle: String,
    val icon: String,
    val latitude: Double,
    val longitude: Double

) : IWithIdModel {
    companion object {
        fun fromVKPlace(place: VKPlace) : PlacePresentationDto {
            val subtitle = place.checkins.toString() + " " + place.address
            val icon = place.icon.orEmpty()
            return PlacePresentationDto(place.id, place.title, subtitle, icon, place.latitude, place.longitude)
        }
    }
}

package com.c.v.ui.check_in_flow.friends.dto

import com.c.v.data.IWithIdModel
import com.c.v.data.network.vkApi.model.VKUser

data class FriendsPresentationDto(
    override val id: Int,
    val title: String,
    val subtitle: String,
    val icon: String

) : IWithIdModel {
    companion object {
        fun fromVKUser(vkUser: VKUser) : FriendsPresentationDto {
            val title = vkUser.firstName + " " + vkUser.lastName
            return FriendsPresentationDto(vkUser.id, title, "" , vkUser.photo100.orEmpty())
        }
    }
}

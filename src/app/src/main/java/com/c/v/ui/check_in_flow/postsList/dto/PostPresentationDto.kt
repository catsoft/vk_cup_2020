package com.c.v.ui.check_in_flow.postsList.dto

import com.c.v.data.IWithIdModel
import com.c.v.data.network.vkApi.model.VKPost

data class PostPresentationDto(
    val postSource: VKPost,
    override val id: Int,
    val text: String,
    val subtitle: String

) : IWithIdModel {
    companion object {
        fun fromVKPost(vkPost: VKPost) : PostPresentationDto {
            return PostPresentationDto(vkPost, 1, "","")
        }
    }
}
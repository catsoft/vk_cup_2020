package com.c.v.ui.check_in_flow.postsList.dto

import com.c.v.data.IWithIdModel
import com.c.v.data.network.vkApi.model.VKPost

data class PostPresentationDto(
    val postSource: VKPost,
    override val id: Int,
    val text: String?,
    val likesCount: String,
    val commentsCount: String,
    val repostsCount: String,
    val viewsCount: String

) : IWithIdModel {
    companion object {
        fun fromVKPost(vkPost: VKPost): PostPresentationDto {
            return PostPresentationDto(
                vkPost,
                vkPost.id,
                vkPost.text,
                vkPost.likes.count.toCount(),
                vkPost.comments.count.toCount(),
                vkPost.reposts.count.toCount(),
                vkPost.views.count.toCount()
            )
        }

        private fun Int.toCount(): String = if (this == 0) "" else this.toString()
    }
}
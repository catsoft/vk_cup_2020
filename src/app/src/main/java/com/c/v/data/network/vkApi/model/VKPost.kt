package com.c.v.data.network.vkApi.model

import com.google.gson.annotations.SerializedName

data class VKPost(

    @SerializedName("id")
    val id: Int,

    @SerializedName("owner_id")
    val ownerId: Int,

    @SerializedName("from_id")
    val fromId: Int,

    @SerializedName("created_by")
    val createdBy: Int,

    @SerializedName("date")
    val date: Long,

    @SerializedName("text")
    val text: String?,

    @SerializedName("reply_owner_id")
    val replyOwnerId: Int,

    @SerializedName("reply_post_id")
    val replyPostId: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("geo")
    val geo: VKGeo?,

    @SerializedName("views")
    val views: VKPostViews,

    @SerializedName("comments")
    val comments: VKPostComments,

    @SerializedName("likes")
    val likes: VKPostLikes,

    @SerializedName("reposts")
    val reposts: VKPostReposts
)


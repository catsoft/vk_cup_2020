package com.c.v.ui.unsubscribe_flow.user_group_detail

import com.c.v.data.IWithIdModel

data class VKUserGroupDetailPresentation(
    override val id: Int,
    val name : String,
    val screenName : String,
    val description : String,
    val members_count : Int,
    val friendsCount : Int?,
    val last_post_date :Long?,
    val shareUrl : String) : IWithIdModel
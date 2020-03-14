package com.c.v.ui.unsubscribe_flow.group_list

import com.c.v.data.IWithIdModel

data class VKUserGroupItemPresentation(
    override val id: Int,
    val name : String,
    val screenName : String,
    val deactivated : String,
    val isMember : Boolean,
    val isClosed : Int,
    val photo50 : String,
    val photo100 : String,
    val photo200 : String,
    val isHiddenFromFeed : Boolean,
    val status : String,
    val members_count : Int,
    val description : String,
    val friendsCount : Int,
    val last_post_date :Long,
    var isSelected : Boolean = false) : IWithIdModel
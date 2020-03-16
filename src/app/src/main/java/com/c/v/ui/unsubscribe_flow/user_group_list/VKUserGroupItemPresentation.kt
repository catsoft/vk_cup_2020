package com.c.v.ui.unsubscribe_flow.user_group_list

import com.c.v.data.IWithIdModel

data class VKUserGroupItemPresentation(
    override val id: Int,
    val name : String,
    val screenName : String,
    val deactivated : String,
    val photo200 : String,
    var isSelected : Boolean = false) : IWithIdModel
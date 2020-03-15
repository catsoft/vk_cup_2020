package com.c.v.domain.userGroups.dto

data class VKUserGroupDto constructor (
    val id : Int,
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
    val friendsCount : Int?,
    val last_post_date :Long?,
    val description : String)


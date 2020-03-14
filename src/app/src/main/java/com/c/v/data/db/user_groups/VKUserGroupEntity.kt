package com.c.v.data.db.user_groups

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vk_user_group")
data class VKUserGroupEntity constructor(
    @PrimaryKey()
    val id: Int,
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
    val last_post_date : Long)
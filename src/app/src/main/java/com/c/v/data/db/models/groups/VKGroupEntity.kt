package com.c.v.data.db.models.groups

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vk_group")
data class VKGroupEntity (
    @PrimaryKey(autoGenerate = true) var localId: Int = 0,
    val remoteId: Int,
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
    val description : String)